import java.util.Iterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    private static Scanner leitura = new Scanner(System.in);
    private static Lista<Curso> listaCursos = new Lista<>(); //Lista de cursos.
    private static Lista<Professor> listaProfessores = new Lista<>(); //Lista de professores.
    private static Lista<Estudante> listaEstudantes = new Lista<>(); //Lista de estudantes.
    private static Lista<File> listaFiles = new Lista<>(); //Lista de arquivos. 
    public static void main(String[] args) {
        

        String[] fileNames = {"professores.txt", "alunos.txt", "cursos.txt"}; //Array de strings relacionada aos arquivos.
    

        for (String fileName : fileNames) {
            File file = new File(fileName);
            listaFiles.adicionar(file);

            try {
                if (!file.exists()) { //Verifica se existe o arquivo, se não existir ele cria!
                    file.createNewFile();
                    System.out.println(fileName + " Criado com sucesso!");
                }   else {
                        System.out.println(file.getName() + " Arquivo já existe. Substituindo conteúdo...");
                    }

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(";");
                        switch (fileName) { // Ler os aquivos e criar as classes a partir dos arquivos.

                            case "curso.txt": //ID;NOME_CURSO;ID_PROFESSOR;MATRICULA_1;MATRICULA_2...;
                                int idCurso = Integer.parseInt(parts[0]); //Convertendo String para inteiro
                                Curso curso = new Curso(idCurso, parts[1]);

                                int idProfessorResponsavel = Integer.parseInt(parts[2]);

                                Professor professorResponsavel = buscarProfessor(idProfessorResponsavel);
                                curso.setProfessor(professorResponsavel);

                                for (String matricula : parts) {
                                    boolean entradaMatriculas = !(matricula.equals(parts[0]) || matricula.equals(parts[1])); 
                                    if (entradaMatriculas) {
                                        Estudante estudante = buscarEstudante(matricula);
                                        curso.adicionarAlunos(estudante);
                                    }
                                }

                                listaCursos.adicionar(curso);
                                break;

                            case "professores.txt": //ID;NOME_PROFESSOR;DEPARTAMENTO
                                int idProfessor = Integer.parseInt(parts[0]);
                                Professor professor = new Professor(idProfessor, parts[1], parts[2]);
                                listaProfessores.adicionar(professor);
                                break;

                            case "alunos.txt": //MATRICULA;NOME_ALUNO
                                Estudante estudante = new Estudante(parts[0], parts[1]);
                                listaEstudantes.adicionar(estudante);
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Menu interativo
        boolean escolha = true;
        
        while (escolha) {
           
            System.out.println("Escolha uma opção:");
            System.out.println("1- Criar curso");
            System.out.println("2- Remover curso");
            System.out.println("3- Adicionar professor");
            System.out.println("4- Remover professor");
            System.out.println("5- Adicionar estudante");
            System.out.println("6- Remover estudante");
            System.out.println("7- Finalizar");

            int opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    criarCurso();
                    break;
                case 2:
                    removerCurso();
                    break;
                case 3:  
                    adicionarProfessores();
                    break;
                case 4:
                    removerProfessores();
                    break;
                case 5:
                    adicionarEstudante();
                    break;
                case 6:
                    removerEstudante();
                    break;
                case 7:
                    escolha= false;
                    break;
                default:
                    System.out.println("Opção não válida!");
            }
        } 
        leitura.close();

        try { //Varre a lista de arquivos e salva as classes dentro do arquivo!
            Iterator<File> iterator = listaFiles.iterator();

            while (iterator.hasNext()) {
                File file = iterator.next();
                if (file.getName().equals("cursos.txt")) { 
                    salvarCursos(file);
                } 
                else if (file.getName().equals("alunos.txt")) {
                    salvarEstudantes(file);
                }
                else if (file.getName().equals("professores.txt")){
                    salvarProfessores(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
    
        System.out.println("Conteúdo salvo com sucesso!");
    }


    //Criando curso
    public static void criarCurso() {
        System.out.print("Digite o ID do curso: ");
        int idCurso = leitura.nextInt();
        leitura.nextLine();
        boolean cursoJaExiste = buscarCurso(idCurso); //Verifica se o curso já existe 
        if (cursoJaExiste){ //Se existir ele imposibilita de criar
            System.out.print("Impossível criar o curso pois já existe um curso com o mesmo ID!");
            return;
        }

        System.out.print("Digite o nome do curso que deseja criar: ");
        String nomeCurso = leitura.nextLine();
        Curso curso = new Curso(idCurso, nomeCurso);
        System.out.print("Digite o ID do professor responsável: ");
        int idProfessor = leitura.nextInt();
        leitura.nextLine();

        Professor professorResponsavel = buscarProfessor(idProfessor);

        if(professorResponsavel == null) { //Verifica se o ID do professor existe para que ele seja atribuido ao curso!
            System.out.print("Impossível criar o curso pois o professor com esse id não foi encontrado!");
            return;
        }
        curso.setProfessor(professorResponsavel);

        boolean maisEstudantes = true; //Atribui os alunos de acordo com a matricula
        while (maisEstudantes) {
            System.out.print("Digite a matrícula do estudante (ou 'sair' para finalizar): ");
            String matricula = leitura.nextLine();
            if (matricula.equalsIgnoreCase("sair")) {
                maisEstudantes = false;
            } else {
                Estudante estudante = buscarEstudante(matricula);
                if (estudante != null) {
                    curso.adicionarAlunos(estudante);
                } else {
                    System.out.println("Estudante não encontrado!");
                }
            }
        }
        listaCursos.adicionar(curso);
        System.out.println("Curso criado com sucesso!");
    }

    //Removendo curso
    private static void removerCurso() {
        System.out.print("Digite o ID do curso a ser removido: ");
        int idCurso = leitura.nextInt();
        leitura.nextLine();
    
        Curso cursoRemovido = null;
        Iterator<Curso> iterator = listaCursos.iterator();
        while (iterator.hasNext()) {
            Curso curso = iterator.next();
            if (curso.getIdCurso() == idCurso) {
                cursoRemovido = curso;
                break;
            }
        }

        boolean cursoRemovidoComSucesso = listaCursos.remover(cursoRemovido);
        if (cursoRemovidoComSucesso) {          
            System.out.println("Curso removido com sucesso!");
        } else {
            System.out.println("Curso não encontrado!");
        }
    }

    //Adicionando professor
    private static void adicionarProfessores() {
        System.out.print("Digite o ID do professor: ");
        int idProfessor = leitura.nextInt();
        leitura.nextLine();

        Professor professorJaAdiconado = buscarProfessor(idProfessor);

        if(professorJaAdiconado != null) { //Verifica se o professor já existe, se sim impossibilita de adicionar
            System.out.print("Impossível adicionar esse professor, pois já existe professor com esse id!");
            return;
        }

        System.out.print("Digite o nome do professor: ");
        String nomeProfessor = leitura.nextLine();

        System.out.print("Digite o departamento do professor: ");
        String departamentoProf = leitura.nextLine();

        Professor professor = new Professor(idProfessor, nomeProfessor, departamentoProf);
        listaProfessores.adicionar(professor);
        System.out.println("Professor adicionado com sucesso!");

    }

    //Remove professor
    private static void removerProfessores() {
        System.out.print("Digite o ID do professor a ser removido: ");
        int idProfessor = leitura.nextInt();
        leitura.nextLine();

        Professor professorRemovido = null;
        Iterator<Professor> iterator = listaProfessores.iterator();
        while (iterator.hasNext()) {
            Professor professor = iterator.next();
            if (professor.getidProfessor() == idProfessor) {
                professorRemovido = professor;
                break;
            }
        }

        boolean professorRemovidoComSucesso = listaProfessores.remover(professorRemovido);
        if (professorRemovidoComSucesso) {          
            System.out.println("Professor removido com sucesso!");
        } else {
            System.out.println("Professor não encontrado!");
        }
    }
    //Adiciona aluno
    private static void adicionarEstudante() {
        System.out.print("Digite a matrícula do estudante que deseja adicionar: ");
        String matricula = leitura.nextLine();

        System.out.print("Digite o nome do estudante que deseja adicionar: ");
        String nomeAluno = leitura.nextLine();

        Estudante estudanteJaAdiconado = buscarEstudante(matricula); 

        if(estudanteJaAdiconado != null) { //Verifica se o aluno já existe, se sim impossibilita de adicionar
            System.out.print("Impossível adicionar esse aluno, pois já existe aluno com esse id!");
            return;
        }

        Estudante estudante = new Estudante(nomeAluno, matricula);
        listaEstudantes.adicionar(estudante);
        System.out.println("Estudante adicionado com sucesso!");

    }

    //Remove aluno
    private static void removerEstudante() {
        System.out.print("Digite a matrícula do estudante a ser removido: ");
        String matricula = leitura.nextLine();

        Estudante estudanteRemovido = null;
        Iterator<Estudante> iterator = listaEstudantes.iterator();
        while (iterator.hasNext()) {
            Estudante estudante = iterator.next();
            if (estudante.getmatricula().equals(matricula)) {
                estudanteRemovido = estudante;
                break;
            }
        }

        boolean estudanteRemovidoComSucesso = listaEstudantes.remover(estudanteRemovido);
        if (estudanteRemovidoComSucesso) {          
            System.out.println("Estudante removido com sucesso!");
        } else {
            System.out.println("Estudante não encontrado!");
        }
    }
    

    //Metodos para buscar curso, professores e alunos!

    private static boolean buscarCurso(int idCurso) { 
        Iterator<Curso> iterator = listaCursos.iterator();
        while (iterator.hasNext()) {
            Curso curso = iterator.next();
            if (curso.getIdCurso() == idCurso) {
                return true;
            }
        }
        return false;
    }
    
    private static Professor buscarProfessor(int idProfessor) {
        Iterator<Professor> iterator = listaProfessores.iterator();

        while (iterator.hasNext()) {
            Professor professor = iterator.next();
            if (professor.getidProfessor() == idProfessor) {
                return professor;
            }
        }
        return null;
    }

    private static Estudante buscarEstudante(String matricula) {
        Iterator<Estudante> iterator = listaEstudantes.iterator();

        while (iterator.hasNext()) {
            Estudante estudante = iterator.next();
            if (estudante.getmatricula().equals(matricula)) {
                return estudante;
            }
        }
        return null;
    }

    //Métodos para salvar no arquivo!

    private static void salvarCursos(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {

            for (Curso curso : listaCursos) {
                writer.write(curso.toString() + System.lineSeparator());
            }
        } catch (Exception e) {

        }
    }

    private static void salvarProfessores(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Professor professor : listaProfessores) {
                writer.write(professor.toString() + System.lineSeparator());
            }
        }
    }

    private static void salvarEstudantes(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Estudante estudante : listaEstudantes) {
                writer.write(estudante.toString() + System.lineSeparator());
            }
        }
    }
    
}