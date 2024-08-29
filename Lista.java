import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Lista<Tipo> implements IList<Tipo>, Iterable<Tipo>{ //Permite o uso do For-Each
    private LinkedList<Tipo> list; //Permite acessar os elementos da lista sucessores e anteriores!

    public Lista(){
        this.list = new LinkedList<>();
    }
    
    //Sobrescreve os metodos

    @Override
    public Iterator<Tipo> iterator(){
        return list.iterator();
    }

    @Override
    public boolean adicionar(Tipo valor) {
        try {
            list.add(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean adicionar(int posicao, Tipo valor){
        try {
            list.add(posicao, valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean adicionar(Collection<Tipo> colecaoValores){
        try {
            list.addAll(colecaoValores);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Tipo obter(int posicao){
        return list.get(posicao);
    }

    @Override
    public Tipo remover(int posicao){
        return list.remove(posicao);
    }

    @Override
    public boolean remover(Tipo valor){
        try {
            list.remove(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remover(Collection<Tipo> colecaoValores){
        try {
            list.removeAll(colecaoValores);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int tamanho(){
        return list.size();
    }

    @Override
    public void limpar(){
        list.clear();
    }
}

