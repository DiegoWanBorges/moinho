import { Employee } from 'core/types/Employee';
import history from 'core/utils/history';

import './styles.scss';

type Props={
    employee:Employee;
    onRemove: (employeeId:number) =>void;
}
const EmployeeCard = ({ employee,onRemove }:Props) => {
    const onEdit = () => {
        history.push(`/registrations/employees/${employee.id}`)
    }
    return (
        <div className="employee-card">
          <div className="employee-card-inf">
            <h5>{employee.name}</h5>
            <small>{employee.sector.name}</small>
          </div>

          <div className="employee-card-action">
                <button
                    onClick={onEdit}
                    type="button"
                    className="btn btn-outline-secondary employee-card-action-btn employee-card-action-btn-edit">
                    EDITAR
                </button>

                <button
                    type="button"
                    className="btn btn-outline-danger employee-card-action-btn"
                    onClick={() => onRemove(employee.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default EmployeeCard;
