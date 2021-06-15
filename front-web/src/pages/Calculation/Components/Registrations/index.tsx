import { Route, Switch } from 'react-router';
import CostCalculationCreate from './Create';
import CalculationList from './List'

import './styles.scss';

function CalculationRegistration() {
    return (
        <div>
            <Switch>
                <Route path="/calculations/registrations" exact>
                    <CalculationList />
                </Route>
                <Route path="/calculations/registrations/create" exact>
                    <CostCalculationCreate/>
                </Route>
                <Route path="/calculations/registrations/:calculationId">
                    <h1>Form</h1>
                </Route>
            </Switch>
        </div>
    );
}
export default CalculationRegistration;