import { Route, Switch } from 'react-router';
import UnityForm from './Form';
import UnityList from './List';

import './styles.scss';

function Unity() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/units" exact>
                    <UnityList/>
                </Route>
                <Route path="/registrations/units/:unitId">
                    <UnityForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default Unity;
