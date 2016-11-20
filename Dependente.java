/** Alunos: Átila D. Grings   Trabalho GB Lab1  Turma: 53 2016/2 */

public class Dependente{
    private String nome;
    private char parentesco;
    
    public Dependente(String nome, char parentesco){
        this.nome = nome;
        setParentesco(parentesco);
    }
    
    public void setParentesco(char parentesco){
        if (parentesco == 'c'|| this.parentesco == 'C')this.parentesco = 'c';
        else if (parentesco == 'f' || this.parentesco == 'F') this.parentesco = 'f';
        else if (parentesco == 'p' || this.parentesco == 'P') this.parentesco = 'p';
        else this.parentesco = 'o';
    }
    
    public String traduzParentesco(){
        if (this.parentesco == 'c')return "Cônjuge";
        else if (this.parentesco == 'f') return "Filho(a)";
        else if (this.parentesco == 'p') return "Progenitor (pais, avós)";
        else this.parentesco = 'o'; return "Outro";
    }
    
    public String toString(){
        return "Nome: "+this.nome + " Parentesco: "+traduzParentesco();
    }    
    
    public String getNome(){
        return this.nome;
    }
    
}