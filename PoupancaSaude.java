/** Alunos: Átila D. Grings   Trabalho GB Lab1  Turma: 53 2016/2 */

public class PoupancaSaude extends Poupanca{
    Teclado t = new Teclado();
    private double saldoVinculado;
    private double saldoFinanciado;
    private Dependente [] dependente;
    
    public PoupancaSaude(int numero, String nome){
        super(numero,nome);
        dependente = new Dependente[5];
    }
    
    public int contaDependentes(){
        int conta = 0;
        for (int i=0; i<dependente.length; i++){
            if (dependente[i] != null) conta++;
        }
        return conta;
    }
    
    public void deposita(double valor){
        double n = contaDependentes();
        if (n==0) {
            this.saldoVinculado = this.saldoVinculado+valor*0.15;
            super.deposita(valor*0.85);}
        if (n==1 || n==2) {
            this.saldoVinculado = this.saldoVinculado+valor*0.2;
            super.deposita(valor*0.80);}   
        if (n==3 || n==4) {
            this.saldoVinculado = this.saldoVinculado+valor*0.3;
            super.deposita(valor*0.70);}  
        if (n==5) {
            this.saldoVinculado = this.saldoVinculado+valor*0.5;
            super.deposita(valor*0.5);}
    }
    
    public double creditaRendimento(double taxa){ 
        double SL = getSaldoLivre()*taxa;// Saldo Livre
        double SV = saldoVinculado*taxa;// Saldo Vinculado
        super.deposita(SL);
        this.saldoVinculado+=SV;
        return SL+SV;
    }
    
    public boolean insereDependente(Dependente dependente){
        ordenaDependentes();
        if (contaDependentes()==this.dependente.length) return false;
        this.dependente[contaDependentes()] = dependente;
        return true;
    }
    
    public int buscaDependente(String nome){
        for (int i=0; i<dependente.length;i++){
            if (dependente[i]!=null){
                if (dependente[i].getNome().compareToIgnoreCase(nome)==0) return i;}
        }
        return 99;
    }
    
    public Dependente retiraDependente(String nome){
        int posicao = buscaDependente(nome);
        if (posicao==99) return null;
        Dependente a = dependente[posicao];
        dependente[posicao] = null;
        return a;
    }
    
    public double retiraSaude(double valor){
        if (valor> saldoVinculado){
            double VD = valor-this.saldoVinculado;// valor Devido
            System.out.println("O valor da despeza é superior ao saldo atual disponível para Saúde. Status geral:\n");
            System.out.println("Saldo devido restante: "+VD);
            System.out.println("Saldo disponível livre: "+getSaldoLivre());
            System.out.println("Deseja utilizar quais das sequintes opções:\n\n1 - Financiamento bancário\n2 - Especificar valor que será abatido do saldo livre");
            int opcao = t.leInt();
            
            if (opcao ==2) { // Especificar valor
                double valor2= t.leDouble("Qual o valor que será abatido do saldo livre?");
                while (valor2>getSaldoLivre() || valor2>VD){ 
                    System.out.println("Valor muito grande, redigite!");
                    valor2= t.leDouble("Qual o valor que será abatido do saldo livre?");
                }
                retira(valor2);
                VD-=valor2;
                saldoVinculado=0;
                    if (VD>0){ // Se ainda existir saldo após usar o saldoLivre então financiar o valor devido com o banco
                        if (saldoFinanciado==0) {
                            saldoFinanciado=VD*1.05;
                            return VD*1.05;}
                        if (saldoFinanciado<=500.00){
                            saldoFinanciado+=VD*1.1;
                            return VD*1.1;}
                        else{
                            saldoFinanciado+=VD*1.15;
                            return VD*1.15;}}}
            else if(opcao==1){//Financiamento
                    if (saldoFinanciado==0) {
                            saldoFinanciado=VD*1.05;
                            saldoVinculado=0;
                            return VD*1.05;}
                    if (saldoFinanciado<=500.00){
                            saldoFinanciado+=VD*1.1;
                            saldoVinculado=0;
                            return VD*1.1;}
                    else{
                            saldoFinanciado+=VD*1.15;
                            saldoVinculado=0;
                            return VD*1.15;}}
  
        }
        else{ 
            saldoVinculado-= valor;}
        return 0;
    }
    
    public double amortizarFinanciamento(double valor){
        if (valor>=saldoFinanciado){
            double pgto = saldoFinanciado*0.05;
            saldoFinanciado =0;
            deposita(pgto);
            return pgto;}
        saldoFinanciado -=valor;
        return 0;
    }
    
    public void ordenaDependentes(){
        int limite = dependente.length;
        Dependente temporario = null;
     for(int i=0;i<limite-1;i++){
          if (dependente[i]==null && dependente[i+1]!=null){
              temporario = dependente[i+1];
              dependente[i]=temporario;
              dependente[i+1]=null;}
     }  
         temporario = null;
     for(int x=0;x<limite;x++){
         for (int i=0; i<limite-1; i++){
              if (dependente[i]!= null && dependente[i+1]!=null){
                  if (dependente[i].getNome().compareToIgnoreCase(dependente[i+1].getNome())>0){
                      temporario = dependente[i];
                      dependente[i]=dependente[i+1];
                      dependente[i+1]=temporario;
                  }
              }
          }
     }
    }
    
    public String toString(){
        String stringDependente="";
        ordenaDependentes();
        
        for (int i=0;i<dependente.length;i++){
            if (dependente[i]!= null)  stringDependente+=" \n"+dependente[i].getNome()+" - ("+dependente[i].traduzParentesco()+")";
        }
        
        if (stringDependente!= ""){
            return super.toString()+"\n\nSaldoVinculado: R$ "+saldoVinculado+"\nSaldoFinanciado: R$ "+saldoFinanciado+"\n\nDependentes:"+stringDependente;}
        else{
            return super.toString()+"\n\nSaldoVinculado: R$ "+saldoVinculado+"\nSaldoFinanciado: R$ "+saldoFinanciado;}
    }
    
    public void exibeDependentes(){
        System.out.println(toString());
    }
}
    
    


