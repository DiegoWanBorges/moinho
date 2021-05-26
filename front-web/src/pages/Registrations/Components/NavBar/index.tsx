import { isAllowedByRole } from 'core/utils/auth';
import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarRegistration = () => (
    <nav className="nav-registration-container">
        <ul className="nav-registration-list">
            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_PARAMETER']) ? (
                    <li>
                        <NavLink to="/registrations/parameters" className="nav-registration-item">Parâmetros</NavLink>
                    </li>
                ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_USER']) ? (
                    <li>
                        <NavLink to="/registrations/users" className="nav-registration-item">Usuários</NavLink>
                    </li>
                ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_GROUP']) ? (
                    <li>
                        <NavLink to="/registrations/groups" className="nav-registration-item">Grupos</NavLink>
                    </li>
                ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_UNITY']) ? (
                    <li>
                        <NavLink to="/registrations/units" className="nav-registration-item">Unidades</NavLink>
                    </li>
                ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_PRODUCT']) ? (
                    <li>
                        <NavLink to="/registrations/products" className="nav-registration-item">Produtos</NavLink>
                    </li>
                ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_SECTOR']) ? (
                    <li>
                        <NavLink to="/registrations/sectors" className="nav-registration-item">Setores</NavLink>
                    </li>
                ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_EMPLOYEE']) ? (
                <li>
                    <NavLink to="/registrations/employees" className="nav-registration-item">Funcionários</NavLink>
                </li>
            ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_LABORCOSTTYPE']) ? (
                <li>
                    <NavLink to="/registrations/laborcosttypes" className="nav-registration-item">Custo funcionário</NavLink>
                </li>
            ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_OPERATIONALCOSTTYPE']) ? (
                <li>
                    <NavLink to="/registrations/operationalcosttypes" className="nav-registration-item">Custo operacional</NavLink>
                </li>
            ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_OCCURRENCE']) ? (
                <li>
                    <NavLink to="/registrations/occurrences" className="nav-registration-item">Ocorrências</NavLink>
                </li>
            ) : null}

            {isAllowedByRole(['ROLE_ADMIN', 'ROLE_REGISTRATION_STATUSPALLET']) ? (
                <li>
                    <NavLink to="/registrations/palletstatus" className="nav-registration-item">Status Pallet</NavLink>
                </li>
            ) : null}
        </ul>

    </nav>
)

export default NavBarRegistration;