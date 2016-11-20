/** Alunos: Átila D. Grings   Trabalho GB Lab1  Turma: 53 2016/2 */

public class Agencia {
    private Poupanca poupanca[];
    private int contador;
    Teclado t = new Teclado();
    
    public Agencia(int nMaximo){
        poupanca = new Poupanca[nMaximo];
        contador = 0;
    }
    
    private int abreConta(){
        int nConta=0;
        if (poupanca.length == contador){
            return -1;}
        else{        
            int tipoConta= t.leInt("\n\fQue tipo de conta deseja criar? \n\n1 - Poupança\n2 - Poupança Saúde\n");
            nConta= t.leInt("\nQual o número da conta?");
            String nomeCliente = t.leString("\nNome do cliente?");
            if (tipoConta ==1){
                poupanca[contador] = new Poupanca(nConta,nomeCliente);
                contador ++;
            }
                else{
                    poupanca[contador]=new PoupancaSaude(nConta,nomeCliente);
                    contador++;
                    char novoDependente;
                    do{
                        novoDependente= t.leChar("\nDeseja adicionar um dependente? S para sim e N para não");
                        if (novoDependente == 'S' || novoDependente == 's'){
                            ((PoupancaSaude)poupanca[contador-1]).insereDependente(new Dependente(t.leString("\nNome do dependente: "),t.leChar("\nParentesco?: \nC - Conjuge\nF - Filho(a)\nP - Progenitor (pais, avós)\nO - Outro\n")));
                        }  
                    }while (novoDependente == 'S' || novoDependente == 's' && ((PoupancaSaude)poupanca[contador-1]).contaDependentes()<5);
            }
        }
        return nConta;
    }
    
    private int buscaConta(int nConta){
        for (int i =0; i<contador;i++){
            if (poupanca[i].getNumero() == nConta) return i;}
        return -1;
    }
    
    public void menuDeTransacoes(){
        int nDigitado = 0;
        do{
            System.out.println("\n-----------------------------------------------------------------");
            System.out.println("01 - Abrir conta");
            System.out.println("02 - Depositar");
            System.out.println("03 - Retirar (saque)");
            System.out.println("04 - Retirar para saúde (saque saúde)");
            System.out.println("05 - Amortizar Financiamento (Quitar)");
            System.out.println("06 - Emitir extrato da conta");
            System.out.println("07 - Creditar rendimentos");
            System.out.println("08 - Inserir dependente");
            System.out.println("09 - Remover dependente");
            System.out.println("10 - Encerrar aplicativo");
            System.out.println("-----------------------------------------------------------------");
            nDigitado = t.leInt("\nEscolha a opção desejada a partir da lista acima:\n");
            
            
            if(nDigitado ==1) //Abrir Conta
                if (poupanca.length==contador) System.out.println("\f\nVocê não pode abrir contas novas nesta agência.");
                else {
                    abreConta();
                    System.out.println("\f\nConta de número "+poupanca[contador-1].getNumero()+" criada com sucesso");}
                
            if(nDigitado ==2) {//Depositar
                int conta = t.leInt("\n\fDigite o número da conta para o depósito: ");
                int posicao = buscaConta(conta);
                if (posicao==-1) System.out.println("\f\nConta inexistente");
                else {
                    double valor = t.leDouble("\nQual o valor a ser depositado?: \n");
                    if (poupanca[posicao] instanceof PoupancaSaude) ((PoupancaSaude)poupanca[posicao]).deposita(valor);
                    else poupanca[posicao].deposita(valor);}
                System.out.println("\f\n");}
               
            if(nDigitado ==3){ //Saque
                int conta = t.leInt("\n\fDigite o número da conta para realizar o saque: ");
                int posicao = buscaConta(conta); 
                if (posicao==-1) System.out.println("\f\nConta inexistente");
                else {
                    if (poupanca[posicao].retira(t.leDouble("\nQual o valor do saque?"))==false) System.out.println("\f\nSaldo insuficiente");}
                System.out.println("\f\n");}
            
            if(nDigitado ==4){//Saque saúde
                int conta = t.leInt("\n\fDigite o número da conta para realizar o saque: ");
                int posicao = buscaConta(conta); 
                if (posicao==-1) System.out.println("\f\nConta inexistente");
                else {
                    if (poupanca[posicao] instanceof PoupancaSaude){
                        double valor = t.leDouble("\nQual o valor será sacado da conta saúde?:");
                        System.out.println("\nValor Financiado: R$ "+((PoupancaSaude)poupanca[posicao]).retiraSaude(valor));}
                    else System.out.println("\f\nEsta conta não é Poupança Saúde");}}

            if(nDigitado ==5){//Amortizar Financiamento
                int conta = t.leInt("\n\fDigite o número da conta para Amortizar o financiamento: ");
                int posicao = buscaConta(conta); 
                if (posicao==-1) System.out.println("\f\nConta inexistente");
                else {
                    if (poupanca[posicao] instanceof PoupancaSaude){
                        double valor = t.leDouble("\nQual o valor será amortizado conta saúde?:");
                        System.out.println("\nGanhou desconto-depósito: R$ "+((PoupancaSaude)poupanca[posicao]).amortizarFinanciamento(valor));}
                    else System.out.println("\f\nEsta conta não é Poupança Saúde");}}
            
            if(nDigitado ==6) {//Emitir extrato da conta
                int numConta = t.leInt("\nDigite o número da conta: ");
                int posicao = buscaConta(numConta);
                if (posicao==-1) System.out.println("\f\nConta Inexistente - não cadastrada nesta agencia");
                else
                    if(poupanca[posicao]instanceof PoupancaSaude) System.out.println("\n"+((PoupancaSaude)poupanca[posicao]).toString());
                    else System.out.println("\n"+poupanca[posicao].toString());}
                    
            if(nDigitado ==7){ //Creditar rendimentos
                double total=0;
                double taxa = t.leDouble("\f\nQual a taxa de rendimentos? (decimal)");
                for (int i=0;i<(contador-1);i++){
                    total+=poupanca[i].creditaRendimento(taxa);}
                System.out.println("Total creditado em todas as contas: R$ "+total);
            }
            
            if(nDigitado ==8){//Inserir dependente
                int conta = t.leInt("\n\fDigite o número da conta para adicionar dependente: ");
                int posicao = buscaConta(conta); 
                if (posicao==-1) System.out.println("\f\nConta inexistente");
                else {
                    if (poupanca[posicao] instanceof PoupancaSaude){
                        if (((PoupancaSaude)poupanca[posicao]).insereDependente(new Dependente(t.leString("\nNome do dependente:"),t.leChar("\nParentesco?: \nC - Conjuge\nF - Filho(a)\nP - Progenitor (pais, avós)\nO - Outro\n"))) == false) System.out.println ("\f\nEsta conta não admite mais dependentes.");}
                    else System.out.println("\f\nEsta conta não é Poupança Saúde");}}
            
            if(nDigitado ==9) {
                int conta = t.leInt("\n\fDigite o número da conta para remover dependente: ");
                int posicao = buscaConta(conta); 
                if (posicao==-1) System.out.println("\f\nConta inexistente"); 
                else {
                    if (poupanca[posicao] instanceof PoupancaSaude){
                        Dependente retirado =((PoupancaSaude)poupanca[posicao]).retiraDependente(t.leString("\nNome do dependente:"));
                        if (retirado == null){
                            System.out.println ("\f\nNão existe dependente com este nome nesta conta.");}}
                    else System.out.println("\f\nEsta conta não é Poupança Saúde");}}
            
        }while (nDigitado!=10);
        System.out.println("\nAplicativo encerrado.");
    }
}
