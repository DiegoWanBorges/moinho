import { Route, Switch } from 'react-router';
import OccurrenceForm from './Form';
import OccurrenceList from './List';


import './styles.scss';

function Occurrences() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/occurrences" exact>
                    <OccurrenceList/>
                </Route>
                <Route path="/registrations/occurrences/:occurrenceId">
                    <OccurrenceForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default Occurrences;
