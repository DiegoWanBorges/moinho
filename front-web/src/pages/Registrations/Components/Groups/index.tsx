import { Route, Switch } from 'react-router';
import GroupForm from './Form';
import GroupList from './List';

import './styles.scss';

function Groups() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/groups" exact>
                    <GroupList/>
                </Route>
                <Route path="/registrations/groups/:groupId">
                    <GroupForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default Groups;
