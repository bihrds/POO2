public class Estudante {
    private String nomeAluno;
    private String matricula;

    public Estudante(String nomeAluno, String matricula){
        this.nomeAluno = nomeAluno;
        this.matricula = matricula;
    }

    public String getnomeAluno(){
        return nomeAluno;
    }

    public String getmatricula(){
        return matricula;
    }

    @Override
    public String toString(){ //MATRICULA;NOME_ALUNO
        return (matricula + ";" + nomeAluno);
    }
    
}
