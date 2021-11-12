public class UserJsonForm {
    User user;
    public UserJsonForm(User user){
        this.user=user;
    }
    public String toJStringNew(){
        String[] fields = new String[4];
        if (user.getName()==null){fields[0]="{\"name\": null,";}
        else{fields[0]="{\"name\": \""+user.getName()+"\",";}
        if (user.getEmail()==null){ fields[1]="\"email\": null,";}
        else{fields[1]="\"email\": \""+user.getEmail()+"\",";}
        if (user.getGender()==null){ fields[2]="\"gender\": null,";}
        else{fields[2]="\"gender\": \""+user.getGender()+"\",";}
        if (user.getStatus()==null) {fields[3]="\"status\": null}";}
        else{fields[3]= "\"status\": \""+user.getStatus()+"\"}";}
        return fields[0]+fields[1]+fields[2]+fields[3];
    }

    public String toJStringUpdate(){
        return "{\"name\": \""+user.getName()+"\"," +
                "\"email\": \""+user.getEmail()+"\"," +
                "\"status\": \""+user.getStatus()+"\"}";
    }

    public String toJStringIntField(String field){
        String[] fields = new String[4];
        int f = RandomCreation.createRandomInt();
        if (field.equals("name")){fields[0]="{\"name\": "+f+"," ;}
        else{fields[0]="{\"name\": \""+user.getName()+"\",";}
        if (field.equals("email")){ fields[1]="\"email\": "+f+"," ;}
        else{fields[1]="\"email\": \""+user.getEmail()+"\",";}
        if (field.equals("gender")){ fields[2]="\"gender\": "+f+",";}
        else{fields[2]="\"gender\": \""+user.getGender()+"\",";}
        if (field.equals("status")) {fields[3]="\"status\": "+f+"}";}
        else{fields[3]= "\"status\": \""+user.getStatus()+"\"}";}
        return fields[0]+fields[1]+fields[2]+fields[3];
    }
    public String wrongJsonData(String field, String changes){
        String request = "";
        if (changes.equals("int")) request = toJStringIntField(field);
        if (changes.equals("null")) {
            user.setField(field, "null");
            request = toJStringNew();
        }
        if (changes.equals("wrongValue")) {
            user.setField(field, "word");
            request = toJStringNew();
        }
        return request;
    }
    public String wrongJsonFormat(){
        String correct =toJStringNew();
        int index = correct.indexOf(",");
        String wrong = correct.substring(0, index-1)+correct.substring(index+1, correct.length()-1);
        return wrong;
    }
}

