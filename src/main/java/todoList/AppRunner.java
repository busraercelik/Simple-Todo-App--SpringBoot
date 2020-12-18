package todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import todoList.models.Task;
import todoList.services.ToDoListService;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class AppRunner implements CommandLineRunner {

    @Autowired
    ToDoListService toDoListService;

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to TODO Command Line App!");

        int response;
        String input;
        do {
            showOptions();
            response=getIntFromUser(scanner);

            switch (response){
                case 1:
                    Task task = new Task();
                    task.setTaskTitle(getStringFromUser(scanner,"Enter the title:"));
                    task.setDescription(getStringFromUser(scanner,"Enter the description:"));
                    toDoListService.createOrUpdateTask(task);
                    break;
                case 2:
                    for (Task t : toDoListService.getAllTasks()) {
                        System.out.println(t);
                    }
                    break;
                case 3:
                    System.out.println("Enter id:");
                    int id = getIntFromUser(scanner);
                    Task taskToUpdate = toDoListService.getTask(Long.valueOf(id));
                    boolean isMarked = taskToUpdate.isMarkedAsComplete();
                    taskToUpdate.setMarkedAsComplete(!isMarked);
                    if(isMarked){
                        System.out.println("Successfully marked as incomplete");
                    }
                    if(!isMarked){
                        System.out.println("Successfully marked as complete");
                    }
                    toDoListService.createOrUpdateTask(taskToUpdate);
                    break;
                case 4:
                    System.out.println("You have exited! :(");
                    break;
            }

        }while (response != 4);


       // System.out.println(toDoListService.createTask(task));
        //System.out.println(toDoListService.getAllTasks());


    }

    private String getStringFromUser(Scanner inputScanner,String msg){
        System.out.println(msg);
        return inputScanner.nextLine();
    }
    private int getIntFromUser(Scanner in){
        int response = in.nextInt();
        in.nextLine();
        return response;
    }
    private void showOptions() {
        System.out.println("Choose one option from the list below: \n" +
                            "(1)-to create a todo\n"+
                            "(2)-to view your todos\n" +
                            "(3)-to mark/unmark your todo as completed/incomplete\n" +
                            "(4)-to quit");
    }
}