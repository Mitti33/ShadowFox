import java.util.*;

class ContactManager{
    
    //an ArrayList of datatype Contact
    public ArrayList<Contact> contacts = new ArrayList<>();

    public String addContact(Contact contact){
        if(contact.name == null || contact.name.isEmpty()){
            return "Name cannot be empty!";
        }
        if(contact.phone == null || contact.phone.isEmpty()){
            return "Phone number cannot be empty!";
        }
        for(int i = 0;i < contact.phone.length();i++){
            char a = contact.phone.charAt(i);
            if(a < '0' || a > '9'){
                return "Ph = only numbers (0-9)!";
            }
        }
        if(contact.email != null && !contact.email.isEmpty() && (!contact.email.contains("@") || !contact.email.contains(".com"))){
            return "Please enter a valid email!";
        }
        if(searchContact(contact.phone) != null) {
            return "Phone number already exists!";
        }
        contacts.add(contact);
        return "";
    }

    public boolean isValid(Contact contact){
        if(contact.name == null || contact.name.isEmpty()){
            return false;
        }
        if(contact.phone == null || contact.phone.isEmpty()){
            return false;
        }
        for(int i = 0;i < contact.phone.length();i++){
            char a = contact.phone.charAt(i);
            if(a < '0' || a > '9'){
                return false;
            }
        }
        if(contact.email != null && !contact.email.isEmpty() && (!contact.email.contains("@") || !contact.email.contains(".com"))){
            return false;
        }
        return true;
    }

    public ArrayList<Contact> getContacts(){
        return contacts;
    }
    
    public int size(){
        return contacts.size();
    }

    public Contact searchContact(String phone) {
        for(Contact c : contacts) {
            if(c.phone.equals(phone)) {
                return c;
            }
        }
        return null;
    }

    public boolean deleteContact(String phone) {
        Contact c = searchContact(phone);

        if(c != null) {
            contacts.remove(c);
            return true;
        }

        return false;
    }

    public boolean updateContact(String oldPhone, String newName, String newPhone, String newEmail) {
        Contact c = searchContact(oldPhone);

        Contact newContact = new Contact(newName, newPhone, newEmail);

        if(c != null && isValid(newContact)) {
            c.name = newName;
            c.phone = newPhone;
            c.email = newEmail;
            return true;
        }

        return false;
    }
}