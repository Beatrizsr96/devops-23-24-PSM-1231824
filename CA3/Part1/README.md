# Technical Report: Virtualization with Vagrant
# Part 1

## Description of Implementation
In this assignment, we used VirtualBox along with Vagrant to create a virtual machine (VM) running Ubuntu. The primary aim was to migrate previous projects into this VM environment and ensure their successful execution.

## Setting Up the Virtual Machine and Isntalling Ubuntu:

1. Begin by setting up a VM using VirtualBox and Vagrant as instructed in the lecture.
The VM was configured with Ubuntu as the guest OS:

 - connect image (ISO) with the Ubuntu18.04 minimal installation media: 
https://help.ubuntu.com/community/Installation/MinimalCD
 - The VM needs 2048 MB RAM
 - Set Network Adapter 1 as Nat
 - Set Network Adapter 2 as Host-only Adapter (vboxnet0) 

2. Start the Vm and install Ubuntu.

3. After starting the VM, log on into the VM and continue the setup:

- Update the packages repositories

```bash
sudo apt update
```

 - Install the network tools
```bash
sudo apt install net-tools
```

 - Edit the network configuration file to setup the IP:
```bash
sudo nano /ect/netplan/01-netcfg.yaml
```

**Make sure the contents of the file are similar to**:

```
network:
 version: 2
 renderer: networkd
 ethernets:
  enp0s3:
   dhcp4: yes
  enp0s8:
   addresses:
    - 192.168.56.5/24
 ```   

- Apply the new changes:
 ```bash
sudo netplan apply
```

- Install openssh-server so that we can use ssh to open secure terminal sessions to theVM (from other hosts)
```bash
sudo apt install openssh-server
```

- Enable password authentication for ssh
```bash
sudo nano /etc/ssh/sshd_config
```

- Uncomment the line *PasswordAuthentication yes*
```bash
sudo service ssh restart
```

- Install an ftp server so that we can use the FTP protocol to transfers files to/fromthe VM (from other hosts)
```bash
sudo apt install vsftpd
```

- Enable write access for vsftpd
```bash
sudo nano /etc/vsftpd.conf
```

- Uncomment the line *write_enable=YES*
```bash
sudo service vsftpd restart
```

4. Install git on Your VM
```bash
sudo apt install git
sudo apt install openjdk-8-jdk-headless
```

5. Cloning the Repository Inside the VM:
Once the VM is up and running, clone your individual repository containing previous project work into the VM environment. This allowed us to work with familiar projects within the VM.

**Your repository must be public in order to be possible to clone it into your VM.**

Navigate to a directory where you want to store your projects, such as `/home/username/projects`.
Use the git command to clone your repository. Replace `<repository-url>` with the URL of your GitHub repository:
``` bash
git clone <repository-url>
```
- Enter your directory containing the projects:
``` bash
cd directory/package/projects
```

6. Setting Up Projects From CA1 and CA2:
Now, it's time to build and execute your two projects: the Spring Boot tutorial basic project and the gradle_basic_demo project from previous assignments.
Dependencies such as Git, JDK, Maven, and Gradle have to be installed in the VM to ensure smooth execution.

6.1 Configure Maven Wrapper and Gradle Wrapper to give executing permissions:
```bash
chmod +x mvnw
chmod +x gradlew
```

6.2. **CA1**:
- Navigate to the project directory:
``` bash
cd directory/with/spring-boot-tutorial-basic
```
- Build the project using Maven:
``` bash
./mvnw clean install
```
- Run the project:
``` bash
./mvnw spring-boot:run
```
- Check that the application is running correctly by accessing it from your host machine’s web browser using the VM’s IP address and the port configured in the project.
```bash
ip addr
```
- put the IP and the port 8080 on the browser address.

6.3. **CA2 Part1**:
- Navigate to the Gradle project directory:
``` bash
cd directory/holding/gradle_basic_demo
```
- Before building, ensure all Gradle dependencies are set up properly. Since some functionalities might not work due to the lack of a desktop environment, adjust the `build.gradle` if necessary.
   
- Build the project using Gradle and run the server:
``` bash
./gradlew build
./gradlew runServer
```
- Build the project in your computer terminal and run the client:
``` bash
gradle runClient --args="192.168.56.5 59001"
```
- The project should run smoothly

6.5. **CA2 Part2**:
- Navigate to the basic folder:
``` bash
cd directory/of/basic/project
```
- Run with gradle:
``` bash
./gradlew build
./gradlew bootRun
```
- Check your VM's IP:
```bash
ip addr
```
- Write `VM'sIP.8080` on your browser address. The application should run smoothly.


## Executing Server-Client Applications:
For projects like the simple chat application, the server was executed within the VM, while clients ran on the host machine.
This configuration ensured efficient resource utilization and seamless communication between components.

## Separation of Server and Client Components:
Running server components within the VM and clients on the host machine allowed for better resource allocation and flexibility.
It also mimicked real-world scenarios where servers are deployed on remote machines while clients interact from local environments.

## Conclusion:
The Part 1 of the assignment focused on virtualization using Vagrant and VirtualBox, demonstrating the migration of projects into a VM environment.

