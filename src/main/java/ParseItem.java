public class ParseItem {
    private char Abertura;
    private char Fechamento;

    public ParseItem(char _Abertura, char _Fechamento)
    {
        Abertura=_Abertura;
        Fechamento=_Fechamento;


    }
    public boolean Validar(char s)
    {
        return s==Fechamento;
    }
}
