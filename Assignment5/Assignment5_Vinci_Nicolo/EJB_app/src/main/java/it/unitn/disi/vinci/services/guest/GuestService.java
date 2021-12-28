package it.unitn.disi.vinci.services.guest;

import it.unitn.disi.vinci.entities.Guest;

public interface GuestService {

    public Guest read(final String name, final String username);
    public boolean create(final String name, final String username);
    public boolean delete(final String name, final String username);
}
