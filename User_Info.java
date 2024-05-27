//Importing files
import java.io.FileWriter;
import java.io.IOException;

//Class User_Info
class User_Info extends Registration_Login {
    //Variable Money
    int money;

    AESEncryptionDecryption crypto = new AESEncryptionDecryption();
    KeyStore keys = new KeyStore();

    //Constructor to create file username.txt and to write initial values in it
    User_Info(String name, String username, String password, String number,String transaction,String FilePath) {
        super(name, username, password, number);
        this.money = 0;
        try {
            FileWriter fwrite = new FileWriter(FilePath + this.getUsername() + ".txt",true);
            fwrite.write(
                crypto.encrypt(String.valueOf(this.money), keys.getKey()) +        "\r\n" + 
                crypto.encrypt(this.getName(), keys.getKey()) +                    "\r\n" + 
                crypto.encrypt(this.getUsername(), keys.getKey()) +                "\r\n" + 
                crypto.encrypt(this.getPassword(), keys.getKey()) +                "\r\n" +
                crypto.encrypt(String.valueOf(this.getNumber()), keys.getKey()) +  "\r\n" + 
                crypto.encrypt(transaction, keys.getKey()));

            fwrite.close();
            // System.out.println("Content is successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    //Message that will be shown on successful registration
    void message() {
        System.out.println("USER GENERATED SUCCESFULLY !");
    }
}
