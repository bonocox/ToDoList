import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListManager {
    // Listas para armazenar as tarefas e seu status de conclusão
    private static ArrayList<String> tasks = new ArrayList<>();
    private static ArrayList<Boolean> isCompleted = new ArrayList<>();

    public static void main(String[] args) {
        // Carrega as tarefas do arquivo ao iniciar o programa
        loadTasks();

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            // Exibe o menu de opções
            System.out.println("\nGerenciador de Tarefas");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Marcar Tarefa como Concluída");
            System.out.println("3. Exibir Tarefas");
            System.out.println("4. Remover Tarefas Concluídas");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            // Executa a ação baseada na opção escolhida
            switch (option) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    markTaskAsCompleted(scanner);
                    break;
                case 3:
                    showTasks();
                    break;
                case 4:
                    removeCompletedTasks();
                    break;
                case 5:
                    // Salva as tarefas no arquivo antes de sair
                    saveTasks();
                    System.out.println("Saindo do Gerenciador de Tarefas.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 5);

        scanner.close();
    }

    // Método para carregar as tarefas de um arquivo
    private static void loadTasks() {
        File file = new File("tasks.txt");
        try {
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    // Divide a linha em status e descrição da tarefa
                    String[] parts = line.split(";", 2);
                    tasks.add(parts[1]);
                    isCompleted.add(parts[0].equals("1"));
                }
                fileScanner.close();
                System.out.println("Tarefas carregadas do arquivo com sucesso.");
            } else {
                System.out.println("Nenhum arquivo de tarefas encontrado. Um novo será criado ao sair.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar tarefas: " + e.getMessage());
        }
    }

    // Método para salvar as tarefas em um arquivo
    private static void saveTasks() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("tasks.txt"));
            for (int i = 0; i < tasks.size(); i++) {
                String status = isCompleted.get(i) ? "1" : "0";
                writer.println(status + ";" + tasks.get(i));
            }
            writer.close();
            System.out.println("Tarefas salvas com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    // Método para adicionar uma nova tarefa
    private static void addTask(Scanner scanner) {
        System.out.print("Digite a descrição da tarefa: ");
        String task = scanner.nextLine();
        tasks.add(task);
        isCompleted.add(false);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    // Método para marcar uma tarefa como concluída
    private static void markTaskAsCompleted(Scanner scanner) {
        showTasks();
        System.out.print("Digite o número da tarefa a marcar como concluída: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            isCompleted.set(taskNumber - 1, true);
            System.out.println("Tarefa marcada como concluída!");
        } else {
            System.out.println("Número de tarefa inválido.");
        }
    }

    // Método para exibir todas as tarefas
    private static void showTasks() {
        System.out.println("\nLista de Tarefas:");
        for (int i = 0; i < tasks.size(); i++) {
            String status = isCompleted.get(i) ? "[Concluída]" : "[Pendente]";
            System.out.println((i + 1) + ". " + tasks.get(i) + " " + status);
        }
    }

    // Método para remover tarefas concluídas
    private static void removeCompletedTasks() {
        // Percorre a lista de trás para frente para evitar problemas de índice
        for (int i = tasks.size() - 1; i >= 0; i--) {
            if (isCompleted.get(i)) {
                tasks.remove(i);
                isCompleted.remove(i);
            }
        }
        System.out.println("Tarefas concluídas removidas.");
    }
}
