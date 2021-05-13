import { Route, Switch } from 'react-router';
import LaborCostTypeForm from './Form';
import LaborCostTypeList from './List';

import './styles.scss';

function LaborCostType() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/laborcosttypes" exact>
                    <LaborCostTypeList/>
                </Route>
                <Route path="/registrations/laborcosttypes/:laborCostTypeId">
                    <LaborCostTypeForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default LaborCostType;
