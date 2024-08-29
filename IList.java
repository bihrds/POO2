import java.util.Collection;

public interface IList<Tipo> {
    public boolean adicionar(Tipo valor);
    public boolean adicionar(int posição, Tipo valor);
    public boolean adicionar(Collection<Tipo> colecaoValores);
    public Tipo obter(int posicao);
    public Tipo remover(int posicao);
    public boolean remover(Tipo valor);
    public boolean remover(Collection<Tipo> colecaoValores);
    public int tamanho();
    public void limpar();
}
