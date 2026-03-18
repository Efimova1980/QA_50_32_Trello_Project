package data_providers;

import dto.Board;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardDP {

    @DataProvider
    public Iterator<Board> dataProviderBoards(){
        List<Board> list = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("src/main/resources/data_provider/boards.csv"))){
            String line = bufferedReader.readLine();
            while (line!=null){
                String[] splitArray = line.split("\n");
                Board board = Board.builder().boardTitle(splitArray[0]).build();
                list.add(board);
                line = bufferedReader.readLine();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return list.listIterator();
    }
}
