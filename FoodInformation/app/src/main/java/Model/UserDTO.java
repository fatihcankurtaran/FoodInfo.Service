package Model;

public class UserDTO extends BaseDTO
{
    private String Name ;
    private String Surname ;
    private String Email ;
    private String Username ;
    private String Password ;
    private UserDTO Result;
    public UserDTO getResult() {
        return Result;
    }

    public void setResult(UserDTO result) {
        Result = result;
    }

    public UserDTO(String username,int modifieduserid) {
        Username = username;
        super.setModifiedUserId(modifieduserid);
    }

    public UserDTO(String username) {
        Username = username;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
