package nz.ac.auckland.concert.server;

import nz.ac.auckland.concert.common.Concert;
import nz.ac.auckland.concert.common.ConcertService;
import org.joda.time.DateTime;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.rmi.*;

/**
 * Created by st970 on 27/07/2017.
 */
public class ConcertServant extends UnicastRemoteObject implements ConcertService {

    private List<Concert> _concerts;
    int _nextId;

    protected ConcertServant() throws RemoteException {
        super();

        _concerts = new ArrayList<Concert>();
        _nextId = 0;
    }

    @Override
    public Concert createConcert(Concert concert) throws RemoteException {
        String _title = concert.getTitle();
        DateTime _date = concert.getDate();

        // Store the new Concert.
        Long uid = new Long(_nextId++);
        Concert newConcert = new Concert(uid, _title,
                _date);

        _concerts.add(newConcert);

        return newConcert;
    }

    @Override
    public Concert getConcert(Long id) throws RemoteException {
        for (Concert con: _concerts) {
            if (con.getId().equals(id)) {
                return con;
            }
        }
        return null;
    }

    @Override
    public boolean updateConcert(Concert concert) throws RemoteException {
        for (Concert con: _concerts) {
            if (con.getId().equals(concert.getId())) {
                _concerts.set(_concerts.indexOf(con), concert);

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteConcert(Long id) throws RemoteException {
        for (Concert con: _concerts) {
            if (con.getId().equals(id)) {
                _concerts.remove(_concerts.indexOf(con));

                return true;
            }
        }
        return false;
    }

    @Override
    public List<Concert> getAllConcerts() throws RemoteException {
        return _concerts;
    }

    @Override
    public void clear() throws RemoteException {
        //removes all Concerts stored b the service.
        _concerts.clear();
    }
}
