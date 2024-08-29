import java.io.Serializable;

public class Professor implements Serializable {
    private static final long serialVersionUID = 10309597L;

    private int idProfessor;
    private String nomeProfessor;
    private String departamentoProf;

    public Professor(int idProfessor, String nomeProfessor, String departamentoProf){
        this.idProfessor = idProfessor;
        this.nomeProfessor = nomeProfessor;
        this.departamentoProf = departamentoProf;
    }

    public int getidProfessor(){
        return idProfessor;
    }

    public String getnomeProfessor(){
        return nomeProfessor;
    }

    public String getdepartamentoProf(){
        return departamentoProf;
    }

    @Override
    public String toString(){ //ID;NOME_PROFESSOR;DEPARTAMENTO
        return ( idProfessor + ";" + nomeProfessor + ";" + departamentoProf);
    }
}