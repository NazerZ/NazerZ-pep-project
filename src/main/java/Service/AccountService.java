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
        if(accountDAO.isUsernameAvailable(account) != null || account.getPassword().length() < 4 || account.getUsername().length() <1){
            return null;
        }
        accountDAO.addUser(account);
        account.setAccount_id(accountDAO.getLogin(account).getAccount_id());
        return account;
    }

}
