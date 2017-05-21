/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto.zad3;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author developer
 */
public class MainWindowController implements Initializable {

    @FXML
    private TextArea keyPubExp;
    @FXML
    private TextArea keyPrivExp;
    @FXML
    private TextArea keyPubModulus;
    @FXML
    private TextArea keyPrivModulus;

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
            pubKey = KeyFiles.readKey(path);
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
            System.err.println(path);
            KeyFiles.writeKey(path, pubKey);
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
            privKey = KeyFiles.readKey(path);
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
            KeyFiles.writeKey(path, privKey);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
