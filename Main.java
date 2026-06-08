import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
  private static final String ARQUIVO = "livros.csv";
  private static ArrayList<Livro> livros = new ArrayList<>();
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    carregarArquivo();
    int opcao = -1;
    while (opcao != 0) {
      System.out.println("\n==== SISTEMA DA BIBLIOTECA ====");
      System.out.println("1 - Cadastrar livro");
      System.out.println("2 - Listar livros");
      System.out.println("3 - Emprestar livro");
      System.out.println("4 - Devolver livro");
      System.out.println("5 - Salvar no arquivo");
      System.out.println("0 - Sair");
      System.out.print("Escolha: ");
      opcao = Integer.parseInt(sc.nextLine());
      switch (opcao) {
        case 1:
          cadastrarLivro();
          break;
        case 2:
          listarLivros();
          break;
        case 3:
          emprestarLivro();
          break;
        case 4:
          devolverLivro();
          break;
        case 5:
          salvarArquivo();
          break;
        case 0:
          salvarArquivo();
          System.out.println("Saindo...");
          break;
        default:
          System.out.println("Opção inválida!");
      }
    }
  }

  private static void cadastrarLivro() {
    System.out.print("Título: ");
    String titulo = sc.nextLine();
    System.out.print("Autor: ");
    String autor = sc.nextLine();
    System.out.print("Páginas: ");
    int paginas = Integer.parseInt(sc.nextLine());
    System.out.print("Ano: ");
    int ano = Integer.parseInt(sc.nextLine());
    System.out.print("Preço: ");


    double preco = Double.parseDouble(sc.nextLine());
    Livro l = new Livro(titulo, autor, paginas, ano, preco, "");
    livros.add(l);
    System.out.println("\nLivro cadastrado!");
  }

  private static void listarLivros() {
    System.out.println("\n==== LISTA DE LIVROS ====");
    if (livros.isEmpty()) {
      System.out.println("Nenhum livro cadastrado.");
      return;
    }
    for (int i = 0; i < livros.size(); i++) {
      System.out.println("\nID: " + i);
      System.out.println(livros.get(i));
    }
  }

  private static void emprestarLivro() {
    listarLivros();
    System.out.print("\nDigite o ID do livro para emprestar: ");
    int id = Integer.parseInt(sc.nextLine());
    if (id < 0 || id >= livros.size()) {
      System.out.println("ID inválido!");
      return;
    }
    Livro l = livros.get(id);
    if (!l.getEmprestadoPara().isEmpty()) {
      System.out.println("Livro já está emprestado!");
      return;
    }
    System.out.print("Emprestar para quem? ");
    String pessoa = sc.nextLine();
    l.setEmprestadoPara(pessoa);
    System.out.println("Livro emprestado!");
  }

  private static void devolverLivro() {
    listarLivros();
    System.out.print("\nDigite o ID do livro para devolver: ");
    int id = Integer.parseInt(sc.nextLine());
    if (id < 0 || id >= livros.size()) {
      System.out.println("ID inválido!");
      return;
    }
    Livro l = livros.get(id);
    if (l.getEmprestadoPara().isEmpty()) {
      System.out.println("Este livro não está emprestado.");
      return;
    }
    l.setEmprestadoPara("");
    System.out.println("Livro devolvido!");
  }

  private static void salvarArquivo() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
      for (Livro l : livros) {
        pw.println(l.toCSVLine());
      }

      System.out.println("Arquivo salvo!");
    } catch (IOException e) {
      System.out.println("Erro ao salvar arquivo: " + e.getMessage());
    }
  }

  private static void carregarArquivo() {
    File arquivo = new File(ARQUIVO);
    if (!arquivo.exists()) {
      System.out.println("Nenhum arquivo encontrado. Será criado depois.");
      return;
    }
    try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        livros.add(Livro.fromCSVLine(linha));
      }
      System.out.println("Arquivo carregado com sucesso!");
    } catch (IOException e) {
      System.out.println("Erro ao carregar arquivo: " + e.getMessage());
    }
  }
}