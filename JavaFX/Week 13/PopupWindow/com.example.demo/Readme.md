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
