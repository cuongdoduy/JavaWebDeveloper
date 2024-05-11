package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder( MethodOrderer.OrderAnnotation.class)
public class Test {
    @LocalServerPort
    private int port;

    private WebDriver driver;

    private String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        baseURL = "http://localhost:" + this.port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    private void doMockSignUp(String firstName, String lastName, String userName, String password){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Create a dummy account for logging in later.
        driver.get(baseURL + "/signup");
        webDriverWait.until( ExpectedConditions.titleContains("Sign Up"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

        webDriverWait.until(ExpectedConditions.urlToBe( baseURL + "/login?success"));
    }

    private void doMockLogin(String userName, String password){
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    private void doMockLogout(){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        WebElement logoutButton = driver.findElement(By.id("logout"));
        logoutButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }

    @org.junit.jupiter.api.Test
    @Order(1)
    public void testSignUpLoginLogout(){
        String firstName = "John";
        String lastName = "Doe";
        String userName = "johndoe";
        String password = "password";

        doMockSignUp(firstName, lastName, userName, password);
        doMockLogin(userName, password);
        doMockLogout();
    }

    @org.junit.jupiter.api.Test
    @Order(2)
    public void testInAccessible(){
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    //Test Note Creation
    @org.junit.jupiter.api.Test
    @Order(3)
    public void testNoteCreation(){
        String userName = "johndoe";
        String password = "password";
        String noteTitle = "Test Note Title";
        String noteDescription = "Test Note Description";
        doMockLogin(userName, password);

        WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note")));
        WebElement addNoteButton = driver.findElement(By.id("add-note"));
        addNoteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleElement = driver.findElement(By.id("note-title"));
        noteTitleElement.click();
        noteTitleElement.sendKeys(noteTitle);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescriptionElement = driver.findElement(By.id("note-description"));
        noteDescriptionElement.click();
        noteDescriptionElement.sendKeys(noteDescription);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit")));
        WebElement noteSubmitButton = driver.findElement(By.id("note-submit"));
        noteSubmitButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals(baseURL + "/result?isSuccess=true", driver.getCurrentUrl());


        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-display")));
        WebElement noteTitleDisplay = driver.findElement(By.id("note-title-display"));
        Assertions.assertEquals(noteTitle, noteTitleDisplay.getText());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description-display")));
        WebElement noteDescriptionDisplay = driver.findElement(By.id("note-description-display"));
        Assertions.assertEquals(noteDescription, noteDescriptionDisplay.getText());
    }

    //Test Note Edit
    @org.junit.jupiter.api.Test
    @Order(4)
    public void testNoteEdit() {
        String userName = "johndoe";
        String password = "password";
        String noteTitle = "Test Note Title";
        String noteDescription = "Test Note Description";
        String newNoteTitle = "New Test Note Title";
        String newNoteDescription = "New Test Note Description";
        doMockLogin( userName , password );

        WebDriverWait webDriverWait = new WebDriverWait( driver , 3 );
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-notes-tab" ) ) );
        WebElement notesTab = driver.findElement( By.id( "nav-notes-tab" ) );
        notesTab.click( );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "edit-note" ) ) );
        WebElement editNoteButton = driver.findElement( By.id( "edit-note" ) );
        editNoteButton.click( );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "note-title" ) ) );
        WebElement noteTitleElement = driver.findElement( By.id( "note-title" ) );
        noteTitleElement.click( );
        noteTitleElement.clear( );
        noteTitleElement.sendKeys( newNoteTitle );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "note-description" ) ) );
        WebElement noteDescriptionElement = driver.findElement( By.id( "note-description" ) );
        noteDescriptionElement.click( );
        noteDescriptionElement.clear( );
        noteDescriptionElement.sendKeys( newNoteDescription );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "note-submit" ) ) );
        WebElement noteSubmitButton = driver.findElement( By.id( "note-submit" ) );
        noteSubmitButton.click( );

        webDriverWait.until( ExpectedConditions.titleContains( "Result" ) );
        Assertions.assertEquals( baseURL + "/result?isSuccess=true" , driver.getCurrentUrl( ) );

        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-notes-tab" ) ) );
        notesTab = driver.findElement( By.id( "nav-notes-tab" ) );
        notesTab.click( );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "note-title-display" ) ) );
        WebElement noteTitleDisplay = driver.findElement( By.id( "note-title-display" ) );
        Assertions.assertEquals( newNoteTitle , noteTitleDisplay.getText( ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "note-description-display" ) ) );
        WebElement noteDescriptionDisplay = driver.findElement( By.id( "note-description-display" ) );
        Assertions.assertEquals( newNoteDescription , noteDescriptionDisplay.getText( ) );
    }

    //Test Note Deletion
    @org.junit.jupiter.api.Test
    @Order(5)
    public void testNoteDeletion() {
        String userName = "johndoe";
        String password = "password";
        String noteTitle = "Test Note Title";
        String noteDescription = "Test Note Description";
        doMockLogin( userName , password );

        WebDriverWait webDriverWait = new WebDriverWait( driver , 3 );
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-notes-tab" ) ) );
        WebElement notesTab = driver.findElement( By.id( "nav-notes-tab" ) );
        notesTab.click( );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "delete-note" ) ) );
        WebElement deleteNoteButton = driver.findElement( By.id( "delete-note" ) );
        deleteNoteButton.click( );

        webDriverWait.until( ExpectedConditions.titleContains( "Result" ) );
        Assertions.assertEquals( baseURL + "/result?isSuccess=true" , driver.getCurrentUrl( ) );

        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-notes-tab" ) ) );
        notesTab = driver.findElement( By.id( "nav-notes-tab" ) );
        notesTab.click( );

        Assertions.assertTrue( driver.findElements( By.id( "note-title-display" ) ).isEmpty( ) );
    }

    //Test Credential Creation
    @org.junit.jupiter.api.Test
    @Order(6)
    public void testCredentialCreation() {
        String userName = "johndoe";
        String password = "password";
        String url = "http://www.google.com";

        doMockLogin( userName , password );

        WebDriverWait webDriverWait = new WebDriverWait( driver , 3 );
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        //Navigate to the credentials tab
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-credentials-tab" ) ) );
        WebElement credentialsTab = driver.findElement( By.id( "nav-credentials-tab" ) );
        credentialsTab.click( );

        //Click on the add credential button
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "add-credential" ) ) );
        WebElement addCredentialButton = driver.findElement( By.id( "add-credential" ) );
        addCredentialButton.click( );

        //Fill in the credential form
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-url" ) ) );
        WebElement credentialUrlElement = driver.findElement( By.id( "credential-url" ) );
        credentialUrlElement.click( );
        credentialUrlElement.sendKeys( url );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-username" ) ) );
        WebElement credentialUsernameElement = driver.findElement( By.id( "credential-username" ) );
        credentialUsernameElement.click( );
        credentialUsernameElement.sendKeys( userName );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-password" ) ) );
        WebElement credentialPasswordElement = driver.findElement( By.id( "credential-password" ) );
        credentialPasswordElement.click( );
        credentialPasswordElement.sendKeys( password );

        //Submit the credential form
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-submit" ) ) );
        WebElement credentialSubmitButton = driver.findElement( By.id( "credential-submit" ) );
        credentialSubmitButton.click( );

        //Check if the credential was created successfully
        webDriverWait.until( ExpectedConditions.titleContains( "Result" ) );
        Assertions.assertEquals( baseURL + "/result?isSuccess=true" , driver.getCurrentUrl( ) );

        //Navigate back to the credentials tab
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-credentials-tab" ) ) );
        credentialsTab = driver.findElement( By.id( "nav-credentials-tab" ) );
        credentialsTab.click( );

        //Check if the credential is displayed
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-url-display" ) ) );
        WebElement credentialUrlDisplay = driver.findElement( By.id( "credential-url-display" ) );
        Assertions.assertEquals( url , credentialUrlDisplay.getText( ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-username-display" ) ) );
        WebElement credentialUsernameDisplay = driver.findElement( By.id( "credential-username-display" ) );
        Assertions.assertEquals( userName , credentialUsernameDisplay.getText( ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-password-display" ) ) );
        WebElement credentialPasswordDisplay = driver.findElement( By.id( "credential-password-display" ) );
        Assertions.assertEquals( password , credentialPasswordDisplay.getText( ) );
    }

    //Test Credential Edit
    @org.junit.jupiter.api.Test
    @Order(7)
    public void testCredentialEdit() {
        String userName = "johndoe";
        String password = "password";
        String url = "http://www.google.com";
        String newUrl = "http://www.udacity.com";
        String newUserName = "newUserName";
        String newPassword = "newPassword";

        doMockLogin( userName , password );

        WebDriverWait webDriverWait = new WebDriverWait( driver , 3 );
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        //Navigate to the credentials tab
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-credentials-tab" ) ) );
        WebElement credentialsTab = driver.findElement( By.id( "nav-credentials-tab" ) );
        credentialsTab.click( );

        //Click on the edit credential button
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "edit-credential" ) ) );
        WebElement editCredentialButton = driver.findElement( By.id( "edit-credential" ) );
        editCredentialButton.click( );

        //Fill in the credential form
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-url" ) ) );
        WebElement credentialUrlElement = driver.findElement( By.id( "credential-url" ) );
        credentialUrlElement.click( );
        credentialUrlElement.clear( );
        credentialUrlElement.sendKeys( newUrl );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-username" ) ) );
        WebElement credentialUsernameElement = driver.findElement( By.id( "credential-username" ) );
        credentialUsernameElement.click( );
        credentialUsernameElement.clear( );
        credentialUsernameElement.sendKeys( newUserName );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-password" ) ) );
        WebElement credentialPasswordElement = driver.findElement( By.id( "credential-password" ) );
        credentialPasswordElement.click( );
        credentialPasswordElement.clear( );
        credentialPasswordElement.sendKeys( newPassword );

        //Submit the credential form
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-submit" ) ) );
        WebElement credentialSubmitButton = driver.findElement( By.id( "credential-submit" ) );
        credentialSubmitButton.click( );

        //Check if the credential was edited successfully
        webDriverWait.until( ExpectedConditions.titleContains( "Result" ) );
        Assertions.assertEquals( baseURL + "/result?isSuccess=true" , driver.getCurrentUrl( ) );

        //Navigate back to the credentials tab
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-credentials-tab" ) ) );
        credentialsTab = driver.findElement( By.id( "nav-credentials-tab" ) );
        credentialsTab.click( );

        //Check if the credential is displayed
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-url-display" ) ) );
        WebElement credentialUrlDisplay = driver.findElement( By.id( "credential-url-display" ) );
        Assertions.assertEquals( newUrl , credentialUrlDisplay.getText( ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-username-display" ) ) );
        WebElement credentialUsernameDisplay = driver.findElement( By.id( "credential-username-display" ) );
        Assertions.assertEquals( newUserName , credentialUsernameDisplay.getText( ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "credential-password-display" ) ) );
        WebElement credentialPasswordDisplay = driver.findElement( By.id( "credential-password-display" ) );
        Assertions.assertEquals( newPassword , credentialPasswordDisplay.getText( ) );
    }

    //Test Credential Deletion
    @org.junit.jupiter.api.Test
    @Order(8)
    public void testCredentialDeletion() {
        String userName = "johndoe";
        String password = "password";
        String url = "http://www.google.com";
        String newUrl = "http://www.udacity.com";
        String newUserName = "newUserName";
        String newPassword = "newPassword";

        doMockLogin( userName , password );

        WebDriverWait webDriverWait = new WebDriverWait( driver , 3 );
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        //Navigate to the credentials tab
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-credentials-tab" ) ) );
        WebElement credentialsTab = driver.findElement( By.id( "nav-credentials-tab" ) );
        credentialsTab.click( );

        //Click on the delete credential button
        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "delete-credential" ) ) );
        WebElement deleteCredentialButton = driver.findElement( By.id( "delete-credential" ) );
        deleteCredentialButton.click( );

        //Check if the credential was deleted successfully
        webDriverWait.until( ExpectedConditions.titleContains( "Result" ) );
        Assertions.assertEquals( baseURL + "/result?isSuccess=true" , driver.getCurrentUrl( ) );

        //Navigate back to the credentials tab
        driver.get( "http://localhost:" + this.port + "/home" );
        webDriverWait.until( ExpectedConditions.titleContains( "Home" ) );

        webDriverWait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "nav-credentials-tab" ) ) );
        credentialsTab = driver.findElement( By.id( "nav-credentials-tab" ) );
        credentialsTab.click( );

        //Check if the credential is displayed
        Assertions.assertTrue( driver.findElements( By.id( "credential-url-display" ) ).isEmpty( ) );
    }
}
