# book-a-book
Backend para la aplicación book a book https://github.com/devmariodiaz/book-a-book-app


# Installing development environment for bookabook

Steps to obtain a functional environment to develop and test bookabook in a Linux system.

## First steps

The first thing that we got to do is clone the repository that contains the software "Linux-Auto-Customizer". This
software consists in a set of scripts to automatically install dependencies, libraries and programs to a Linux
Environment. It can be used in many distros, but in this guide we suppose that our environment is Ubuntu Linux. It
may be the same or similar instructions in related distros. You can skip it this step and install the dependencies 
manually in your own.

We need to install `git` and `git-lfs` in our system using the package manager:
```bash 
sudo apt install -y git git-lfs
```

We can clone the repository anywhere, for example in our HOME folder:

```bash
cd $HOME
git clone https://github.com/AleixMT/Linux-Auto-Customizer
cd Linux-Auto-Customizer
git-lfs pull
git checkout develop
sudo bash src/core/install.sh -v -o customizer
```

The previous commands will install the software, so it can be accessed using the link `customizer-install` and
`customizer-uninstall` software if everything is okay.

## Resolving dependencies

Execute the next orders:
```bash
sudo customizer-install -v -o psql
bash cutomizer-install -v -o jdk17 pgadmin postman ideau  # ideac 
```

This will install:
* **JDK17:** Java development kit. Contains the interpreter for the Java programming language `java` and the tool to
  manipulate the certificates used in the java VM `keytool`
* **psql:** PostGreSQL, SQL DataBase engine
* **IntelliJ IDEA Community / IntelliJ IDEA Ultimate:** IDE with a high customization via plugins to work with Java.
  The  ultimate edition needs a license; The community version, which is commented out, has also all the required
  features to work with the project.
* **pgadmin:** Graphical viewer of the PostGreSQL DataBase using a web browser.
* **postman:** UI used to manage API calls and requests. Useful for testing and for keeping record of interesting API
  calls. Has cloud synchronization, environments variables, workflows, etc.

This will set up the software with some new soft links and aliases, which will be populated in your environment by
writing to the `.bashrc` of your HOME folder. Things like JAVA_HOME environment variables or problems with binaries in 
the path are solved this way. 

## Setting up database connection
Log in as the `postgres` user:
```bash
sudo su - postgres
```

Then create the user that the installation will use:
```bash
createuser --interactive --pwprompt
```
Notice that there are other ways of doing this. You can also do it directly by submitting orders to the database from
this user, but in this case it is easier if you have this binary wrapper. It will ask for a password, consider this the
database password. Also make the user superuser if asked. 

Then we need to create the database for our software:
```bash
createdb bookabook
```

#### Connect to the database manually using terminal
``` 
psql -d bookabook -h localhost -p 5432 -U YOUR_USER
```

#### Connect to the database manually using pgAdmin
Use `pgadmin` program to connect to the database using a graphical interface. To do so, just launch pgadmin by calling
the binary. You can access the interface using a web browser pointing to `localhost:5050`. Add a server by submitting 
the data in the form. 






## Compile software
#### Terminal
In a terminal, clone the repository, enter its root directory and run the maven wrapper with the `compile` option. By
using the installation before, all the needed
software and its dependencies and environmental variables are resolved, so they can be found by maven and our editor:

```bash
git clone https://github.com/AleixMT/book-a-book
cd book-a-book
./mvnw compile
```

This will download all the necessary libraries to execute the project.
This script has also been modified to accept different configuration files, kill anything that is using the port that we
are going to deploy our app and is easy to modify the certificates that it uses.

#### IDE
You can also compile the software by using the IDE. To do so, after cloning the repository `cd` into it and call the
`ideac` or `ideau`, depending on which we installed. I will use `ideau`.
```bash
git clone https://github.com/AleixMT/book-a-book
cd book-a-book
ideau
```
You should wait until IntelliJ IDEA has loaded your project. There may be some additional file indexing, but you can
start using the IDE to configure it. You will need to set up the Software Development Kit (SDK) of Java that you are 
going to use. The customizer has installed jdk17 in this path: `/home/YOUR_USER/.customizer/bin/jdk17`. Configure the 
SDK to use this path or provide another JDK17. 

After that you should be able to compile the software by clicking in the hammer in the upper right part of the IDE
window. This is the easier step.

## Run software
#### Terminal
To compile using only a terminal, clone the repository, enter it and run it using the maven wrapper. By using the
installation before,
all the needed
software and its dependencies and environmental variables are resolved, so they can be found by maven and our IDE:
```bash
#git clone https://github.com/AleixMT/book-a-book  # Not needed if we already cloned it
cd book-a-book
./mvnw spring-boot:run
```

#### IDE
After cloning the repository `cd` into it and call the `ideac` or `ideau`, depending on which we installed. I will use
`ideau`.
```bash
git clone https://github.com/AleixMT/book-a-book
cd book-a-book
ideau
```
You should wait until IntelliJ IDEA has loaded your project. There may be some additional file indexing, but you can
start using the IDE to configure it.

First thing is you got to go to the right upper part of the window in order to reach compile, run and debug menu.
You should import the run configurations that are stored in the `.run/` folder of this project. You also may need
to add the Java interpreter that we installed. It will be located in the folder `$HOME/.customizer/java`. You can print
this direction by typing in a terminal:
```bash
echo $JAVA_HOME
```

To use the IDE to run the software there are two options:
###### Using the same maven wrapper
There are two ways of running the software from our IDE:

First we can use the bash script called `mvnw`. This is the script used in the previous terminal section.
We can call the script as explained from inside the IDE.
We can configure a run configuration that executes the maven wrapper or import the one in the `.run/` folder by
clicking *Add configuration...* in the upper right part of the IDE window. This will load a window where we can
configure how this configuration works:
- *Script path*: `$HOME/book-a-book/mvnw`
- *Working directory*: `$HOME/book-a-book`
- *Script options*: `spring-boot:run`
- *Interpreter path*: `/bin/bash`
- *Execute in the terminal*: Yes

After that we only need to click to the green play button with this configuration selected to run the program. We will
see the output in the terminal at the bottom.

###### Using the autoconfiguration of the IDE
Go to the class `BookABookApplication` and in the line where we declare the class:
```java
public class BookABookApplication {
    // ...
}
```

You can click right click and run the application.

## Debug software

Open the IDE, and perform the same steps to run the app with the autoconfiguration, but when you right-click select
`debug book-a-bookApplication`. You can add breakpoints on the desired lines by clicking on the left of the line and
control the execution by using the menu at the bottom of the IDE.

To send API requests to test the application you may use `postman`, but you can proceed with `curl` if you prefer to
only use the terminal.
