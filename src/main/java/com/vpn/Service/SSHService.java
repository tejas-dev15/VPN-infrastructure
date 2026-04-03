package com.vpn.Service;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class SSHService {

    private final String HOST = "192.168.0.200";
    private final String PASSWORD = "POPWW2005";
    private final String USER = "tejas";

    public String executeCommand(String command){
        StringBuilder output = new StringBuilder();
        try{
            JSch jSch = new JSch();
            Session session = jSch.getSession(USER, HOST, 22);
            session.setPassword(PASSWORD);

            session.setConfig("StrictHostKeyChecking","no");
            session.setConfig("PreferredAuthentications", "password");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] buffer = new byte[1024];

            while(true){
                while(in.available() > 0){

                    int i = in.read(buffer,0,1024);
                    if(i<0) break;
                    output.append(new String(buffer,0,i));
                }
                if(channel.isClosed()) break;
            }
            session.disconnect();
            channel.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("SSHP command Failed");
        }
        return output.toString();
    }

    public String generatePrivateKey(){
        return executeCommand("wg genkey").trim();
    }

    public String generatePublicKey(String privatekey){
        String command = "echo " + privatekey + " |wg pubkey";
        return executeCommand(command).trim();
    }

    public void AddPeer(String publicKey, String ip){
        String command = String.format(
                "sudo wg set wg0 peer %s allowed-ips %s/32",
                 publicKey,ip
        );
        executeCommand(command);
    }

    public void removePeer(String publicKey){
           String command = String.format(
                   "sudo wg set wg0 peer %s remove",
                   publicKey
           );
           executeCommand(command);
    }
}
