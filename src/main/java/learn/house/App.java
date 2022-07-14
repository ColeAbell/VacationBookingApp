package learn.house;

import learn.house.data.GuestFileRepository;
import learn.house.data.HostFileRepository;
import learn.house.data.ReservationFileRepository;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.ui.ConsoleIO;
import learn.house.ui.Controller;
import learn.house.ui.View;

public class App {

    public static void main(String[] args) {
        GuestFileRepository guestRepository = new GuestFileRepository("./data/guest-test.txt");
        HostFileRepository hostRepository = new HostFileRepository("./data/host-test.txt");
        ReservationFileRepository reservationRepository = new ReservationFileRepository("./data/reservation_data_test");
        GuestService guestService = new GuestService(guestRepository);
        HostService hostService = new HostService(hostRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);
        ConsoleIO io = new ConsoleIO();
        View view = new View(io);
        Controller controller = new Controller(guestService, hostService, reservationService, view);
        controller.run();
    }
}
