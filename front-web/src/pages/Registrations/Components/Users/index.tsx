import { Route, Switch } from 'react-router';
import UserList from './List';
import './styles.scss';

function Users() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/users" exact>
                    <UserList />
                </Route>
                <Route path="/registrations/users/:userId">
                    
                </Route>
            </Switch>
        </div>
    );
}
export default Users;
