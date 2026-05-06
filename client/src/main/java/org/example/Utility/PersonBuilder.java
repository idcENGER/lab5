package org.example.Utility;

import org.example.MusicBands.*;

import java.util.Scanner;
public class PersonBuilder {

    public static Person buildPerson(){
        return new Person(askName(),askHeight(),askPassportID(),askHairColor(),askLocation());
    }

    static Scanner scanner = new Scanner(System.in);

    private static String askName(){
        while (true){
            System.out.print("Введите имя артиста(Имя начать с большой буквы. Имя может содержать только буквы): ");
            try {
            String name = XmlHandler.SpaceRemover(scanner.nextLine());
            if (name.matches("^[A-ZА-ЯЁ][a-zа-яё]+(?:[- ][A-ZА-ЯЁ][a-zа-яё]+)*$")) {return name;}
        }catch (IllegalArgumentException e){
                System.out.println("В имени могут быть только буквы.");
            }
        }
    }

    private static Float askHeight(){
        float input;
        do {
            System.out.print("Введите рост артиста(Число с плавающей точкой от 0 до 300): ");
            try {
                input = Float.parseFloat(XmlHandler.SpaceRemover(scanner.nextLine()).replace(",","."));
            }catch (NumberFormatException exception){
                System.out.println("Данные в неверном формате:" +exception.getMessage());
                input = 0f;
            }
            if (input <= 0f || input >= 300f){
                System.out.println("Некорректные данные: рост не может быть отрицательным или большим 300");
            }
        }while (input <= 0f || input >= 300f);
        return input;
    }

    private static String askPassportID() throws IllegalArgumentException {
        while (true){
            System.out.print("Введите пасспортные данные(в паспортных данных до 22 символов(буквы и цифры): ");
            try {
                String id = XmlHandler.SpaceRemover(scanner.nextLine());
                if (id.length() >22) {
                    throw new IllegalArgumentException("В паспортных данных не больше 22 символов.");
                }
                if (!id.matches("[A-ZА-ЯЁa-zа-яё0-9]+$")){
                    throw new IllegalArgumentException("В паспортных даннаых могут быть только цифры и буквы");
                }
                if(id.isBlank()){
                    throw new NullPointerException("Строка не может быть пустой");
                }
                return id;
            }catch (NullPointerException | IllegalArgumentException e){
                System.out.println("Некорректные данные:" + e.getMessage());
            }
        }
    }

    private static Color askHairColor(){
        while (true){
            Color.colors();
            System.out.print("Введите цвет или его номер из списка: ");
            try {
                String color = scanner.nextLine();
                if(color.matches("^[0-9]+")){
                    return Color.values()[Integer.parseInt(color)];
                }else{
                    return Color.valueOf(XmlHandler.SpaceRemover(color.toUpperCase()));
                }
            }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex){
                System.out.println("Некорректный цвет. Попробуйте еще раз.");
            }
        }
    }

    private static Location askLocation(){
        while(true) {
            try {
                System.out.print("-1.8 * 10 ^ 308 <= x <= -1.8 * 10 ^ 308, -2*63 <= y <= 2^63-1,-3.4*10^38 <= z <= 3.4*10^38");
                System.out.print("Введите координаты в формате x,y,z: ");
                String str = XmlHandler.SpaceRemover(scanner.nextLine());
                if (!str.substring(str.length()-1).matches("^[0-9]")){
                    throw new IllegalArgumentException();
                }
                String[] location = str.split(",");
                double x = Double.parseDouble(XmlHandler.SpaceRemover(location[0].replace(",",".")));
                long y = Long.parseLong(XmlHandler.SpaceRemover(location[1]));
                float z = Float.parseFloat(XmlHandler.SpaceRemover(location[2].replace(",",".")));
                return new Location(x,y,z);
            }catch (IllegalArgumentException | IndexOutOfBoundsException ex){
                System.out.println("Некорректные данные. " + ex.getMessage());
            }
        }

    }

    public static Person updatePerson(Person person){
        person.setName(askName());
        person.setHeight(askHeight());
        person.setPassportID(askPassportID());
        person.setHairColor(askHairColor());
        person.setLocation(askLocation());
        return person;
    }

}
