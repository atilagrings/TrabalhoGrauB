/** Alunos: Átila D. Grings   Trabalho GB Lab1  Turma: 53 2016/2 */

public class Poupanca{
 private int numero;
 private String cliente; // nome do cliente
 private double saldoLivre;

    public Poupanca(int numero, String nome){
        this.numero = numero;
        this.cliente = nome;
        this.saldoLivre = 0;
    }

    public void deposita(double valor){
        this.saldoLivre = this.saldoLivre +valor;
    }

    public boolean retira(double valor){
        if (this.saldoLivre < valor)
        return false;
        this.saldoLivre = this.saldoLivre - valor;
        return true;
    }

    public double creditaRendimento(double taxa){ // a taxa é decimal
        double rendimento = taxa * this.saldoLivre;
        this.saldoLivre += rendimento;
        return rendimento;
    }
 
    public String toString(){
        return "\fNum: " + this.numero + " Cliente: " + this.cliente+ "\nSaldo livre: R$ " + this.saldoLivre;
    }

    public String getCliente(){
        return this.cliente;}
        
    public int getNumero(){
        return this.numero;}
        
    public double getSaldoLivre(){
        return this.saldoLivre;}
        
}