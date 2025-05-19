# Ridely

Ridely is a cutting-edge application designed specifically for enhancing the taxi-riding experience in the heart of
Aracaju.
This project embodies the core functionalities necessary for a comprehensive ride-hailing application, offering users a
seamless,
efficient, and reliable way to navigate the bustling streets of Aracaju by taxi.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java JDK 11 or newer installed
- Gradle (depending on what you use for your project)
- IntelliJ IDEA

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/l3co/wec.git
    ```

2. Navigate into the project directory:

    ```bash
    cd repository
    ```

## Configuring IntelliJ IDEA to read the env.development file

1. Open IntelliJ IDEA and navigate to the project you want to configure.

2. In the top menu, click on `Run` and then on `Edit Configurations...`.

3. In the window that opens, locate your project's configuration on the left panel.

4. On the right panel, locate the `Environment variables` section.

5. Click on the addition icon (`+`) to add a new environment variable.

6. In the `Name` field, type the name of the environment variable you want to add. This should be the same name you used in the `env.development` file.

7. In the `Value` field, type the value of the environment variable. This should be the same value you used in the `env.development` file.

8. Click on `OK` to close the window and save your changes.

Now, IntelliJ IDEA is configured to read the `env.development` file and your environment variables will be available to your project.

## Install Dependencies

Run the following command to install the necessary dependencies:

```bash
gradle build
```

## Running the application

```bash
gradle bootRun
```

### Running the tests

```bash
gradle test
```

### API Documentation

You can access the API documentation in two ways:

1. http://localhost:8080/api-docs
2. http://localhost:8080/swagger-ui.html
