package br.com.trustsystems.entities;

import br.com.trustsystems.persistence.Persistent;

import java.io.Serializable;

public abstract class AbstractEntity<ID extends Serializable> implements Persistent<ID>
{
    public abstract void setId(final ID id);

    @Override
    public boolean equals(Object obj)
    {

        if (null == obj)
        {
            return false;
        }

        if (this == obj)
        {
            return true;
        }

        if (!getClass().equals(obj.getClass()))
        {
            return false;
        }

        AbstractEntity<?> that =  null;

        try
        {
            that = (AbstractEntity<?>) obj;
        } catch (ClassCastException cce)
        {
            return false;
        }

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode()
    {
        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
