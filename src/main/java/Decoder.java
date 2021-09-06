import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Decoder {

    List<String> parts;
    char[] Aberturas={'(','[','{','<'};
    char[] Fechamentos={')',']','}','>'};

    String path;

    public Decoder()
    {

    }
    private ParseItem VerificaCaracterAbre(char s)
    {
        for(int counter=0;counter<Aberturas.length;counter++)
        {
            if (Aberturas[counter]==s)
                return new ParseItem(Aberturas[counter],Fechamentos[counter]);
        }
        return null;
    }
    private boolean VerificaCaracterFecha(Stack<ParseItem> pilha, char s)
    {
        for(int counter=0;counter<Fechamentos.length;counter++)
        {
            if (Fechamentos[counter]==s)
            {
                if(!pilha.empty())
                    return pilha.pop().Validar(s);
                else
                    return false;
            }

        }
        return true;
    }

    public boolean Decodificar()
    {
        if (!Gettxt())
            return true;
        System.out.println("Processando arquivo...");
        ProcessarArquivo();
        return true;
    }
    private void ProcessarArquivo()
    {
        List<String> lista = new ArrayList<>();
        for(String s : parts)
        {
            lista.add(DecodificarParte(s));
        }
        AtualizarFile(lista);
    }
    private void  AtualizarFile(List<String> list)
    {
        String data="";
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(path);
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao sobescrever arquivo!");
        }
        writer.print("");
        for(String text : list) {
            writer.println(text);
        }
        writer.close();

        System.out.println("Arquivo processado!");
    }
    private String DecodificarParte(String s)
    {
        Stack<ParseItem> pilha = new Stack<>();
        for(int counter=0;counter<s.length();counter++)
        {
            ParseItem p = VerificaCaracterAbre(s.charAt(counter));
            if(p!=null)
                pilha.add(p);
            else if (!VerificaCaracterFecha(pilha,s.charAt(counter)))
                return s+" - Inválido";

        }
        return s+" - OK";
    }


    private boolean  Gettxt()
    {
        System.out.println("Digite o caminho do arquivo txt...");
        Scanner s = new Scanner(System.in);
        String Data;
        path=s.nextLine();
        try {

            byte[] encoded = Files.readAllBytes(Paths.get(path));
            Data=new String(encoded, StandardCharsets.UTF_8);
            GetDataFromTXT(Data);
            return true;

        } catch (IOException e) {
            System.out.println("Arquivo não encontrado!");
            return false;
        }
    }


    private  void GetDataFromTXT(String s)
    {
        parts= new ArrayList<>();
        String textAux;
        for(String text : s.split("\n"))
        {
            textAux=text.replaceAll("\r","");
            if(!textAux.equals(""))
                parts.add(textAux);
        }


    }
}
