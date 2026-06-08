/**
 * Livro.java
 * Classe que representa um livro da biblioteca.
 *
 * Contém atributos, getters/setters, toString e métodos para
 * conversão para/desde uma linha no arquivo (CSV com ';' como separador).
 */
public class Livro {
    // Atributos do livro
    private String titulo;
    private String autor;
    private int paginas;
    private int ano;
    private double preco;
    private String emprestadoPara; // null ou "" se não emprestado

    // Construtor completo
    public Livro(String titulo, String autor, int paginas, int ano, double preco, String emprestadoPara) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.ano = ano;
        this.preco = preco;
        this.emprestadoPara = (emprestadoPara == null) ? "" : emprestadoPara;
    }

    // Getters e Setters (encapsulamento)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getEmprestadoPara() {
        return emprestadoPara;
    }

    public void setEmprestadoPara(String emprestadoPara) {
        this.emprestadoPara = (emprestadoPara == null) ? "" : emprestadoPara;
    }

    @Override
    public String toString() {
        String emprestimo = (emprestadoPara == null || emprestadoPara.isEmpty())
                ? "Disponível"
                : "Emprestado para: " + emprestadoPara;
        return String.format(
                "Título: %s\nAutor: %s\nPáginas: %d\nAno: %d\nPreço: R$ %.2f\n%s\n",
                titulo, autor, paginas, ano, preco, emprestimo
        );
    }

    public String toCSVLine() {
        String tituloClean = titulo.replace(";", ",").replace("\n", " ");
        String autorClean = autor.replace(";", ",").replace("\n", " ");
        String emprestadoClean = (emprestadoPara == null ? "" : emprestadoPara)
                .replace(";", ",")
                .replace("\n", " ");
        return String.format(
                "%s;%s;%d;%d;%.2f;%s",
                tituloClean, autorClean, paginas, ano, preco, emprestadoClean
        );
    }

    public static Livro fromCSVLine(String line) {
        String[] partes = line.split(";", -1);
        if (partes.length != 6) {

            throw new IllegalArgumentException("Linha CSV inválida: " + line);
        }
        String titulo = partes[0];
        String autor = partes[1];
        int paginas = Integer.parseInt(partes[2]);
        int ano = Integer.parseInt(partes[3]);
        double preco = Double.parseDouble(partes[4]);
        String emprestadoPara = partes[5];
        return new Livro(titulo, autor, paginas, ano, preco, emprestadoPara);
    }
}9