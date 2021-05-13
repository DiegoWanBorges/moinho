import { Role } from 'core/types/User';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import Select from 'react-select';
import { toast } from 'react-toastify';
import './styles.scss';

export type FormState = {
    name: string;
    email: string;
    roles: Role[];
    password: string;
    passwordRepeat: string;
    updatePassword: boolean;
}

type ParamsType = {
    userId: string;
}
function UserForm() {
    const { register, handleSubmit, errors, setValue, getValues, control } = useForm<FormState>();
    const history = useHistory();
    const { userId } = useParams<ParamsType>();
    const isEditing = userId !== 'new';
    const [isLoadingRoles, setIsLoadingRoles] = useState(false);
    const [roles, setRoles] = useState<Role[]>([]);
    const [updatePassword, setUpdatePassword] = useState(false);
   
    useEffect(() => {
        !isEditing && setUpdatePassword(true);
    }, [isEditing])
    
    useEffect(() => {
        setIsLoadingRoles(true);
        makePrivateRequest({ url: `/roles/` })
            .then(response => {
                setRoles(response.data)

            })
            .finally(() => {
                setIsLoadingRoles(false);
            })
    }, [])
    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/users/${userId}` })
                .then(response => {
                    setValue('name', response.data.name);
                    setValue('email', response.data.email);
                    setValue('roles', response.data.roles)
                    setValue('password', "12345678");
                    setValue('passwordRepeat', "12345678");
                })
        }
    }, [userId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        console.log(data);
        makePrivateRequest({
            url: isEditing ? `/users/${userId}` : '/users/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Usuário salvo com sucesso!")
                history.push('/registrations/users/')
            })
            .catch(() => {
                toast.error("Erro ao salvar usuário!")
            })
    }

    const handleCancel = ()=>{
        history.push("./")
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="user-form">
            <div className="user-form-main">
                <div className="user-form-name">
                    <label className="label-base" >Nome Completo:</label>
                    <input
                        name="name"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 2, message: "O campo deve ter minímo 2 caracteres" },
                            maxLength: { value: 50, message: "O campo deve ter no maximo 10 caracteres" },
                        })}
                    />
                    {errors.name && (
                        <div className="invalid-feedback d-block">
                            {errors.name.message}
                        </div>
                    )}
                </div>
                <div className="user-form-email">
                    <label className="label-base">E-mail:</label>
                    <input
                        type="email"
                        name="email"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 5, message: "O campo deve ter minímo 5 caracteres" },
                            maxLength: { value: 60, message: "O campo deve ter no maximo 60 caracteres" },
                        })}
                    />
                    {errors.email && (
                        <div className="invalid-feedback d-block">
                            {errors.email?.message}
                        </div>
                    )}
                </div>

            </div>
            <div className="user-form-select">
                <label className="label-base">Selecione as permissões:</label>
                <Controller
                    as={Select}
                    name="roles"
                    rules={{ required: true }}
                    control={control}
                    isLoading={isLoadingRoles}
                    options={roles}
                    getOptionLabel={(option: Role) => option.authority}
                    getOptionValue={(option: Role) => String(option.id)}
                    classNamePrefix="roles-select"
                    isMulti
                    placeholder="Permissões"
                />
                {errors.roles && (
                    <div className="invalid-feedback d-block">
                        Campo obrigatório
                    </div>
                )}
            </div>

            { isEditing ? (
                <>
                    <input
                        type="checkbox"
                        name="updatePassword"
                        ref={register}
                        value={updatePassword.toString()}
                        onChange={() => setUpdatePassword(!updatePassword)}

                    />
                 Alterar Senha?
                </>
            ) : null}

            {
                updatePassword && (
                    <div className="user-form-password">
                        <div className="user-form-password-1">
                            <label className="label-base">Senha:</label>

                            <input
                                type="password"
                                className="input-base"
                                name="password"
                                ref={register({
                                    required: "Campo obrigatório",
                                    minLength: { value: 6, message: "O campo deve ter minímo 6 caracteres" },
                                    maxLength: { value: 20, message: "O campo deve ter no maximo 20 caracteres" },
                                })}
                            />
                            {errors.password && (
                                <div className="invalid-feedback d-block">
                                    {errors.password?.message}
                                </div>
                            )}
                        </div>
                        <div className="user-form-password-2">
                            <label className="label-base">Confirme a senha:</label>
                            <input
                                name="passwordRepeat"
                                type="password"
                                className="input-base"
                                ref={register({
                                    required: "Campo obrigatório",
                                    minLength: { value: 6, message: "O campo deve ter minímo 6 caracteres" },
                                    maxLength: { value: 20, message: "O campo deve ter no maximo 20 caracteres" },
                                    validate: {
                                        value: value => value === getValues('password') || "As senhas não são iguais ",
                                    }
                                })}
                            />
                            {errors.passwordRepeat && (
                                <div className="invalid-feedback d-block">
                                    {errors.passwordRepeat?.message}
                                </div>
                            )}
                        </div>
                    </div>
                )
            }

            <div className="user-form-actions">
                <button
                    className="btn btn-outline-danger"
                    onClick={handleCancel}
                >
                    CANCELAR
                </button>
                <button className="btn btn-primary user-form-btn-save">
                    SALVAR
                </button>
            </div>
        </form>
    );
}
export default UserForm;
