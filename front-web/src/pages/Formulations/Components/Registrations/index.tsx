import { Route, Switch } from 'react-router';
import FormulationForm from './Form';
import FormulationList from './List';

import './styles.scss';

function FormulationRegistration() {
    return (
        <div>
            <Switch>
                <Route path="/formulations/registrations" exact>
                    <FormulationList/>
                </Route>
                <Route path="/formulations/registrations/:formulationId">
                    <FormulationForm />
                </Route>
            </Switch>
        </div>
    );
}
export default FormulationRegistration;
