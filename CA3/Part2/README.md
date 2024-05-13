# Technical Report: Setting up Virtual Environment with Vagrant
# Overview

In Part 2, a virtual environment was setted up using Vagrant to execute a Spring Boot application (gradle "basic" version), focusing on virtualization with Vagrant.

## Vagrant Analysis
Vagrant allows defining virtual environments as code using a simple and reproducible configuration file (Vagrantfile). Supports various virtualization and cloud providers, including VirtualBox, VMware, Hyper-V, Docker, and more. Vagrant provides built-in support for provisioning tools like shell scripts, enabling automated software configuration. 
Vagrant environments can be version-controlled using Git, allowing teams to collaborate efficiently. Ensures consistent environments across different development machines, reducing the problems like, things that work on our machine and don't work on a different machine (specially when working with different operating systems).

## VirtualBox Analysis
VirtualBox is available for Windows, macOS, Linux, and Solaris, providing consistent virtualization capabilities across different operating systems. It offers guest additions that enhance the virtual machine's performance and integration with the host system, including shared folders, clipboard integration, and dynamic screen resizing. 

VirtualBox supports a variety of network modes, including NAT, bridged, host-only, and internal networking, offering flexibility in configuring network connectivity. VirtualBox provides an extensive API and SDK for automation, integration with third-party tools, and customization of virtual machine settings.


### Part 1: Initial Setup
Study the Vagrantfile provided in the repository "https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/" to understand how it creates and provisions two VMs: 
	- "web" for running Tomcat and the Spring Boot application;
	- "db" for executing the H2 server database.

### Part 2: Copying Vagrantfile to Your Repository
Create a directory "CA3/Part2".

```bash
cd path/to/directory/
mkdir CA3/Part2
```
Copy the Vagrantfile from the cloned repository to "CA3/Part2" directory in your repository.

```bash
cd /CA3/Part2/
git clone <repository_URL>
```

### Part 3: Updating Vagrantfile Configuration
Open the Vagrantfile in your repository.
Update the configuration to use your own Gradle version of the Spring Boot application. 
Ensure that the Vagrantfile reflects the changes necessary to use the H2 server in the "db" VM. 

**Vagrantfile:**

```ruby
# See: https://manski.net/2016/09/vagrant-multi-machine-tutorial/
# for information about machine names on private network
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # We want to access H2 console from the host using port 8082
    # We want to connet to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setting H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  #============
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      # sudo apt-get install git -y
      # sudo apt-get install nodejs -y
      # sudo apt-get install npm -y
      # sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install -y tomcat9 tomcat9-admin
      # If you want to access Tomcat admin web page do the following:
      # Edit /etc/tomcat9/tomcat-users.xml
      # uncomment tomcat-users and add manager-gui to tomcat user

      # Change the following command to clone your own repository!
      git clone https://github.com/Beatrizsr96/devops-23-24-PSM-1231824.git
      cd ~/devops-23-24-PSM-1231824/CA2/Part2/react-and-spring-data-rest-basic
      chmod +wrx *
      ./gradlew clean build
      # To deploy the war file to tomcat9 do the following command:
      nohup ./gradlew bootrun > /home/vagrant/spring-boot-app.log 2>&1 &
      # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
  end
end
```
You should change:
	- the `git clone` command and insert your repository url;
	- the `cd ~/devops-23-24-PSM-1231824/CA2/Part2/react-and-spring-data-rest-basic` command with your repository directory.


### Part 4: Vagrant commands

Use `vagrant up` command to run vagrantfile.
Open your browser and write http://<your ip>:<port> (example: 192.168.56.10:8080). 
This ip and port are the ones that you wrote in your vagranfile.

If you need to update your vagrantfile and rerun it again, you'll need to delete the VMs created previously.

For this matter you can run the following command for each VM:
```bash
# remove db VM
vagrant destroy db

#remove web VM
vagrant destroy web
```
You can also use your VM reference. If you run `vagrant  global -status` you'll have access to your existing VMs and respective reference. 

### Part 5: Committing Changes
After this, you can commit your Vagrantfile into your repository.

## Conclusions
Vagrant and VirtualBox are powerful tools that complement each other, with Vagrant providing a higher-level abstraction for managing virtual environments and VirtualBox offering robust virtualization capabilities. 



# Alternative Solution Using VMware

An alternative to VirtualBox is VMware, which offers virtualization solutions for running multiple operating systems on a single physical machine. VMware provides robust features for creating and managing virtual machines (VMs).

## Analysis comparing VMware and VirtualBox
- Features

VMware provides a wide range of advanced features suitable for enterprise environments, including live migration (vMotion), high availability, and distributed resource scheduling (DRS).
On the other hand, VirtualBox offers essential virtualization features such as snapshots, shared folders, and USB device support, but lacks some of the advanced features found in VMware.


- Performance

VMware typically offers better performance, especially in enterprise environments, with optimized hypervisor technology and advanced features.
VirtualBox performance can sometimes be lower, especially under heavy workloads, due to its focus on ease of use and compatibility.


- Integration

VMware seamlessly integrates with enterprise-grade infrastructure components such as storage arrays, networking equipment, and cloud management platforms.
VirtualBox generally used as a standalone virtualization solution and may require additional integration efforts for enterprise-level deployments.


## Overview
In this alternative solution, we'll use VMware to create and manage virtual machines for running the Spring Boot application and the H2 database. VMware provides a user-friendly interface and powerful features for virtualization, making it a suitable alternative to VirtualBox.


### Part 1: Setting Up VMware VMs

1. Download and Install VMware

Download and install VMware Workstation (MAC) or VMware Fusion (Windows and Linux) on your machine.

### Part 2: Creating Vagrantfile for Your Repository
**Vagrantfile:**

```ruby
Vagrant.configure("2") do |config|
  # Set the VMware box name
  config.vm.box = "ubuntu/focal64"

  # Common provision for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    # Use the VMware provider
    db.vm.provider "vmware_desktop" do |vmware|
      # Specify VMware-specific configurations
      vmware.memory = 1024
    end

    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # Port forwarding for H2 console and server
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # Download H2 database
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # Run H2 server process
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    # Use the VMware provider
    web.vm.provider "vmware_desktop" do |vmware|
      # Specify VMware-specific configurations
      vmware.memory = 1024
    end

    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # Port forwarding for Tomcat
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      sudo apt install -y tomcat9 tomcat9-admin

      # Change the following command to clone your own repository!
      git clone https://github.com/Beatrizsr96/devops-23-24-PSM-1231824.git
      cd ~/devops-23-24-PSM-1231824/CA2/Part2/react-and-spring-data-rest-basic
      chmod +wrx *
      ./gradlew clean build
      nohup ./gradlew bootrun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
  end
end
```

### Part 3: Running Virtual Machines

1. Accessing Applications
Access the Spring Boot application by navigating to its IP address or hostname in a web browser. Similarly, access the H2 database console using its IP address and configured port.

### Part 4: VMware Commands

1. Use `vagrant up --provider=vmware_desktop` command to run vagrantfile using VMware

If you need to update your vagrantfile and rerun it again, you'll need to delete the VMs created previously.


2. Destroy the VMs when done
```bash
vagrant destroy -f
```


## Conclusion

By utilizing VMware for virtualization, you can create and manage virtual machines efficiently for running applications and databases. VMware offers a user-friendly interface, robust features, and excellent performance for virtualized environments. This alternative solution provides a reliable and scalable approach to virtualization, offering advantages similar to VirtualBox with a different set of features and capabilities.



