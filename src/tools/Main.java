package tools;

import tools.core.TestException;
import tools.core.TicketMaker;

public class Main {

    public static void main(String[] args) throws TestException {
	TicketMaker maker = new TicketMaker();
	String fileName = "data/ticketData1.txt";
	maker.exec(fileName);
    }

}
