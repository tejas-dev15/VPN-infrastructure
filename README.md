# 🚀 WireGuard VPN Control Plane (Spring Boot)

A fully functional **VPN control plane backend** built using Spring Boot that dynamically provisions and manages WireGuard clients through SSH automation.

This project goes beyond a typical CRUD app — it implements a **real infrastructure system** involving authentication, networking, security, and system-level control.

---

## 🧠 Architecture Overview

The system is divided into two main components:

### 🔹 Control Plane (Backend)

Responsible for managing VPN clients and server configuration.

* JWT Authentication & RBAC
* VPN client provisioning
* Dynamic IP allocation
* WireGuard peer management via SSH
* Config & QR generation

### 🔹 Data Plane (VPN Traffic)

Handles actual encrypted communication.

* Client connects via WireGuard
* Encrypted tunnel established
* Traffic routed through VPN server to internet

---

## ⚙️ Tech Stack

* **Backend:** Spring Boot, Spring Security
* **Database:** MySQL
* **VPN:** WireGuard
* **SSH Automation:** JSch
* **Security:** JWT, BCrypt
* **Utilities:** ZXing (QR generation)

---

## 🔐 Features

### ✅ Authentication & Authorization

* JWT-based authentication
* Role-Based Access Control (ADMIN / USER)

### ✅ VPN Client Management

* Create / delete VPN clients
* Assign unique IPs dynamically (10.0.0.0/24)
* Store client metadata in DB

### ✅ WireGuard Automation

* Generate key pairs via server
* Add/remove peers using SSH
* Real-time server updates

### ✅ Config Generation

* Auto-generate WireGuard config files
* Ready-to-use client configs

### ✅ QR Code Onboarding

* Convert config → QR code
* Scan via mobile → instant VPN connection

### ✅ Peer Revocation

* Remove client from server + database
* Immediate access termination

### ✅ Centralized Exception Handling

* Structured error responses
* Clean API behavior

---

## 🔁 System Flow

### Control Plane

```text
Spring Boot → SSH → WireGuard Server
```

### Data Plane

```text
Client → Encrypted Tunnel → Server → Internet → Server → Client
```

---

## 📡 API Endpoints

### 🔐 Auth

```
POST /auth/login
```

---

### 👤 Users (Admin)

```
POST /users
```

---

### 🌐 VPN Clients

```
POST   /vpn/clients
GET    /vpn/clients
GET    /vpn/clients/{id}
DELETE /vpn/clients/{id}
```

---

### ⚙️ Config & QR

```
GET /vpn/clients/{id}/config
GET /vpn/clients/{id}/qr
```

---

## 🧪 Example Workflow

1. Admin logs in → receives JWT
2. Admin creates VPN client
3. Backend:

   * Allocates IP
   * Generates keys
   * Adds peer to WireGuard
4. Config is generated
5. User scans QR → connects instantly
6. Admin can revoke access anytime

---

## 🔐 Security Considerations

* Password hashing using BCrypt
* JWT token expiration
* Role-based endpoint protection
* SSH command restriction (sudoers config)
* Input validation on all APIs

---

## ⚠️ Important Notes

* Requires a running WireGuard server
* SSH access must be configured
* `wg` command must be allowed via passwordless sudo
* Environment variables should be used for secrets

---

## 🚀 Future Improvements

* Refresh token mechanism
* Frontend dashboard (Admin panel)
* Multi-server support
* Usage monitoring & analytics
* Deployment on AWS / cloud infra

---

## 💡 Key Learning Outcomes

* Real-world backend architecture design
* Control Plane vs Data Plane separation
* Secure system design with JWT & RBAC
* SSH-based infrastructure automation
* Networking concepts using WireGuard

---

## Final Thoughts

This project demonstrates how backend systems can **control real infrastructure**, not just manage data.

It bridges:

```text
Backend Engineering + Networking + Security + System Design
```

---

## 📌 Author

**Tejas Dange**

Computer Science Engineering Student

Passionate about backend systems, networking, and real-world projects

---

