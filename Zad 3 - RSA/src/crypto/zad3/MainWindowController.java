package crypto.zad3;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindowController {

    /*
    From the keys tab
     */
    @FXML
    private TextArea keyPubExp;
    @FXML
    private TextArea keyPrivExp;
    @FXML
    private TextArea keyPubModulus;
    @FXML
    private TextArea keyPrivModulus;

    /*
    From the signing tab
     */
    @FXML
    private TextArea signingMessage;
    @FXML
    private TextArea resultSignature;

    /*
    From the verification tab
     */
    @FXML
    private TextArea verificationMessage;
    @FXML
    private TextArea verificationSignature;

    private Stage stage;
    private RSAKey privKey;
    private RSAKey pubKey;

    private void ShowNewPrivKey() {
        String exponent, modulus;
        exponent = privKey.getExponent().toString(16);
        modulus = privKey.getModulus().toString(16);
        keyPrivExp.setText(exponent);
        keyPrivModulus.setText(modulus);
    }

    private void ShowNewPubKey() {
        String exponent, modulus;
        exponent = pubKey.getExponent().toString(16);
        modulus = pubKey.getModulus().toString(16);
        keyPubExp.setText(exponent);
        keyPubModulus.setText(modulus);
    }

    @FXML
    private void generateKeys() {
        RSAKeysGenerator kg = new RSAKeysGenerator();
        kg.generateKeys(1024);
        privKey = kg.getPrivateKey();
        pubKey = kg.getPublicKey();
        ShowNewPrivKey();
        ShowNewPubKey();
    }

    @FXML
    private void loadPubKeyFromFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Wybierz plik klucza publicznego");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Plik klucza publicznego", "*.pub"),
                new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt")
        );
        File f = fc.showOpenDialog(stage);
        if (f != null) {
            String path = f.getAbsolutePath();
            try {
                pubKey = KeyFiles.readKey(path);
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
                alert.show();
            }
            if (pubKey != null) {
                ShowNewPubKey();
            }
        }
    }

    @FXML
    private void savePubKeyToFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Wybierz miejsce zapisu pliku klucza publicznego");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Plik klucza publicznego", "*.pub"),
                new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt")
        );
        File f = fc.showSaveDialog(stage);
        if (f != null) {
            String path = f.getAbsolutePath();
            try {
                KeyFiles.writeKey(path, pubKey);
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
                alert.show();
            }
        }
    }

    @FXML
    private void loadPrivKeyFromFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Wybierz plik klucza prywatnego");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Plik klucza prywatnego", "*.priv"),
                new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt")
        );
        File f = fc.showOpenDialog(stage);
        if (f != null) {
            String path = f.getAbsolutePath();
            try {
                privKey = KeyFiles.readKey(path);
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
                alert.show();
            }
            if (privKey != null) {
                ShowNewPrivKey();
            }
        }
    }

    @FXML
    private void savePrivKeyToFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Wybierz miejsce zapisu pliku klucza prywatnego");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Plik klucza prywatnego", "*.priv"),
                new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt")
        );
        File f = fc.showSaveDialog(stage);
        if (f != null) {
            String path = f.getAbsolutePath();
            try {
                KeyFiles.writeKey(path, privKey);
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
                alert.show();
            }
        }
    }

    @FXML
    private void signMessage() {
        try {
            if (privKey == null || pubKey == null) {
                Alert alert = new Alert(AlertType.INFORMATION, "Najpierw wskaż klucze");
                alert.show();
                return;
            }
            String message = signingMessage.getText();
            byte[] msg = message.getBytes(StandardCharsets.UTF_8);
            byte[] s = RSAAlgorithm.generateBlindSignature(msg, privKey, pubKey);
            String signature = Utils.ByteArrayToBigInt(s).toString(16);
            resultSignature.setText(signature);
        } catch (NoSuchAlgorithmException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
            alert.show();
        }
    }

    @FXML
    private void verifyMessage() {
        if (privKey == null || pubKey == null) {
            Alert alert = new Alert(AlertType.INFORMATION, "Najpierw wskaż klucze");
            alert.show();
            return;
        }
        String message = verificationMessage.getText();
        String signature = verificationSignature.getText();
        byte[] msg = message.getBytes(StandardCharsets.UTF_8);
        byte[] s = Utils.BigIntToByteArray(new BigInteger(signature, 16));
        try {
            boolean result = RSAAlgorithm.verifySignature(msg, s, pubKey);
            if (result == true) {
                Alert alert = new Alert(AlertType.INFORMATION, "Udana weryfikacja wiadomości");
                alert.show();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION, "Weryfikacja wiadomości nie powiodła się");
                alert.show();
            }
        } catch (NoSuchAlgorithmException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
            alert.show();
        }

    }

    private void LoadMessageFromFile(TextArea field, String title) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        File f = fc.showOpenDialog(stage);
        if (f != null) {
            try {
                byte[] b = Files.readAllBytes(f.toPath());
                String message = new String(b, StandardCharsets.UTF_8);
                field.setText(message);
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
                alert.show();
            }
        }
    }

    @FXML
    public void LoadMessageToVerify() {
        LoadMessageFromFile(verificationMessage, "Wybierz plik z wiadomością");
    }

    @FXML
    public void LoadMessageToSign() {
        LoadMessageFromFile(signingMessage, "Wybierz plik z wiadomością");
    }

    @FXML
    public void LoadSignatureToVerify() {
        LoadMessageFromFile(verificationSignature, "Wybierz plik z podpisem");
    }

    @FXML
    public void saveResultSignature() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Wybierz miejsce zapisu podpisu");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt"));
        File f = fc.showSaveDialog(stage);
        if (f != null) {
            try {
                byte[] res = resultSignature.getText().getBytes(StandardCharsets.UTF_8);
                Files.write(f.toPath(), res);
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getLocalizedMessage());
                alert.show();
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
