package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    //requirements
    //login register

    AccountDAO accountDAO;
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    public Account getAccount(Account account){
        return accountDAO.getLogin(account);
    }
    public Account addAccount(Account account){
        if(getAccount(account) == null || account.getPassword().length() < 4 || account.getUsername().length() <1){
            return null;
        }
        return accountDAO.addUser(account);
    }

}
