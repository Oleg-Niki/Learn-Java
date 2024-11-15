**1. Set Up Your JavaFX Project**
Prerequisites:
Install Scene Builder.

_Ensure you have JavaFX SDK installed and integrated with your IDE (e.g., IntelliJ IDEA)._

**2. Design Login Screen (Scene Builder)**
Open Scene Builder.

Create a New FXML File:

Save the file as login.fxml.

Add UI Elements:

Drag a VBox (Vertical Box) to the scene.
Add the following components:
Label: Title (e.g., "Login to Your Account").
TextField: For entering the username.
PasswordField: For entering the password.
Button: Login button (label it as "Login").
Add a Hyperlink for "Sign Up".
Set IDs for Components:

_Set unique fx:id for each component (e.g., usernameField, passwordField, loginButton, etc.).
Save the design._

**3. Design Sign-Up Screen**
Create Another FXML File:

Save it as signup.fxml.
Add UI Elements:

Use VBox or GridPane.
Add:
TextField: Username.
PasswordField: Password.
PasswordField: Confirm password.
Button: Sign Up button.
Add a Hyperlink for "Go to Login".

_Set appropriate fx:id for each component._

**4. Design Main Screen**
Create Another FXML File:

Save it as main.fxml.
Add UI Elements:

Use VBox with a MenuBar or HBox:
Label: "Welcome, [Username]".
Button: For "Deposit".
Button: For "Withdraw".
Button: For "View Statement".
Button: Logout.
_
Set fx:id for each button (e.g., depositButton, withdrawButton, etc.)._


**FOR CLOUD DATABASE:**
Steps to Set Up Yugabyte Cloud
Ensure Your Database Has a users Table Run the following SQL script to create the users table if it doesn't already exist:

sql
Copy code
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
Update Database Credentials Replace the placeholders in the code:

<your-yugabyte-cloud-host>: Your YugabyteDB instance hostname.
<your-database-name>: Name of the database.
<your-username>: Your database username.
<your-password>: Your database password.
Using JDBC Driver
Ensure your project has the PostgreSQL JDBC driver for connecting to YugabyteDB. Add this to your Maven pom.xml file:

xml
Copy code
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>
Test the Sign-Up Workflow
Launch your app.
Navigate to the sign-up screen.
Fill out the username and password fields.
Click the "Sign Up" button.
If the user is created successfully, you'll see a success alert.
The new user will be saved in your YugabyteDB users table.

