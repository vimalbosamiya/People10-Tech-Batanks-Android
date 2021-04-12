package com.batanks.nextplan

class JavaToKotlin {}

    /*class ContactsAdapter(context:Context, contactList:List<Contact>, listener:ContactsAdapterListener):RecyclerView.Adapter<ContactsAdapter.MyViewHolder>(), Filterable {
        private val context:Context
        private val contactList:List<Contact>
        private val contactListFiltered:List<Contact>
        private val listener:ContactsAdapterListener
        val itemCount:Int

            get() {
                return contactListFiltered.size
            }

        // name match condition. this might differ depending on your requirement
        // here we are looking for name or phone number match

        val filter:Filter
            get() {
                return object:Filter() {
                    protected fun performFiltering(charSequence:CharSequence):FilterResults {
                        val charString = charSequence.toString()
                        if (charString.isEmpty())
                        {
                            contactListFiltered = contactList
                        }
                        else
                        {
                            val filteredList = ArrayList<Contact>()
                            for (row in contactList)
                            {
                                if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence))
                                {
                                    filteredList.add(row)
                                }
                            }
                            contactListFiltered = filteredList
                        }
                        val filterResults = FilterResults()
                        filterResults.values = contactListFiltered
                        return filterResults
                    }
                    protected fun publishResults(charSequence:CharSequence, filterResults:FilterResults) {
                        contactListFiltered = filterResults.values as ArrayList<Contact>
                        notifyDataSetChanged()
                    }
                }
            }



        init{
            this.context = context
            this.listener = listener
            this.contactList = contactList
            this.contactListFiltered = contactList
        }

        fun onCreateViewHolder(parent:ViewGroup, viewType:Int):MyViewHolder {
            val itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_row_item, parent, false)
            return MyViewHolder(itemView)
        }

        fun onBindViewHolder(holder:MyViewHolder, position:Int) {
            val contact = contactListFiltered.get(position)
            holder.name.setText(contact.getName())
            holder.phone.setText(contact.getPhone())
            Glide.with(context)
                    .load(contact.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.thumbnail)
        }

        inner class MyViewHolder(view:View):RecyclerView.ViewHolder(view) {
            var name:TextView
            var phone:TextView
            var thumbnail:ImageView
            init{
                name = view.findViewById(R.id.name)
                phone = view.findViewById(R.id.phone)
                thumbnail = view.findViewById(R.id.thumbnail)
                view.setOnClickListener(object:View.OnClickListener() {
                    fun onClick(view:View) {
                        // send selected contact in callback
                        listener.onContactSelected(contactListFiltered.get(getAdapterPosition()))
                    }
                })
            }
        }

        interface ContactsAdapterListener {
            fun onContactSelected(contact:Contact)
        }
    }*/



/*public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>
implements Filterable {
    private Context context;
    private List<Contact> contactList;
    private List<Contact> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public ContactsAdapter(Context context, List<Contact> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Contact contact = contactListFiltered.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());

        Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Contact> filteredList = new ArrayList<>();
                    for (Contact row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Contact>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Contact contact);
    }
}*/

