import java.util.Iterator;
import java.io.Serializable;

public class Curso implements Serializable{ //salvar o estado atual dos objetos em arquivos em formato binário , sendo assim esse estado poderá ser recuperado posteriormente recriando o objeto em memória assim como ele estava no momento da sua serialização.
    private static final long serialVersionUID = 10309597L;

    private int idCurso;
    private String nomeCurso;
    private Professor professorResponsavel;
    private Lista<Estudante> listaEstudante = new Lista<>();

    public Curso(int idCurso, String nomeCurso){
        this.idCurso = idCurso;
        this.nomeCurso = nomeCurso; 
    }

     public int getIdCurso() {
        return idCurso;
    }

    public void setProfessor (Professor professorResponsvael){
        this.professorResponsavel = professorResponsvael;
    }

    public void adicionarAlunos(Estudante estudante){
        listaEstudante.adicionar(estudante);
    }

    public String mostrarAlunos(){
        Iterator<Estudante> iterator = listaEstudante.iterator();
        String alunos = new String();

        while (iterator.hasNext()) {
            Estudante estudante = iterator.next();
            alunos= alunos + estudante.getmatricula() + ";";
        }
        return alunos;

    }

    @Override
    public String toString(){////ID;NOME_CURSO;ID_PROFESSOR;MATRICULA_1;MATRICULA_2...;
        return (idCurso + ";" + nomeCurso + ";"  + professorResponsavel + ";" + mostrarAlunos());
    }
}