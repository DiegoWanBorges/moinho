import { Route, Switch } from 'react-router';
import EmployeeForm from './Form';
import EmployeeList from './List';
import './styles.scss';

function Employee() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/employees" exact>
                    <EmployeeList />
                </Route>
                <Route path="/registrations/employees/:employeeId">
                    <EmployeeForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default Employee;
