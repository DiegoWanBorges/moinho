import { Route, Switch } from 'react-router';
import OperationalCostTypeForm from './Form';
import OperationalCostTypeList from './List';
import './styles.scss';

function OperationalCostType() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/operationalcosttypes" exact>
                    <OperationalCostTypeList />
                </Route>
                <Route path="/registrations/operationalcosttypes/:operationalCostTypeId">
                    <OperationalCostTypeForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default OperationalCostType;
