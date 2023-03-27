package src;

public class View {
    
    public static void getExpMessage(int opt){

        switch(opt){

            case 0:
            System.out.print("Ocorreu uma exceção inespecíficada");
            break;

            case 1:
            System.out.print("Ocorreu uma exceção ao abrir o arquivo");
            break;

            case 2:
            System.out.print("Ocorreu uma exceção ao localizar o arquivo");
            break;

            case 3:
            System.out.print("Ocorreu uma exceção ao ler o arquivo");
            break;
        }
        
        System.out.println(" - Código da exceção: " + opt);
    }
}
