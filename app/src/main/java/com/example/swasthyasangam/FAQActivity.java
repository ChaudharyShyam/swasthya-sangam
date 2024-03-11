package com.example.swasthyasangam;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Faq> faqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);

        recyclerView = findViewById(R.id.recyclerView);

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        FaqAdapter FaqAdapter = new FaqAdapter(faqList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(FaqAdapter);
    }


    private void initData() {
        faqList = new ArrayList<>();
        faqList.add(new Faq("Is your application available for both Android and iOS devices?", "Yes, Swasthya Sangam is available for both Android and iOS platforms. You can download it from Google Play Store for Android devices and the App Store for iOS devices."));

        faqList.add(new Faq("How can I contact customer support for assistance?", "You can reach our customer support team via email at support@swasthyasangam.com or by calling our helpline at +91-XXX-XXXXXXX. Our support team is available 24/7 to assist you with any queries or concerns."));

        faqList.add(new Faq("What languages does the Swasthya Sangam application support?", "Currently, Swasthya Sangam supports English and Hindi languages. We are working to add support for more languages in the future updates."));

        faqList.add(new Faq("Are my personal details and medical information secure with Swasthya Sangam?", "Yes, we prioritize the security and privacy of your data. Swasthya Sangam employs industry-standard encryption protocols to safeguard your personal and medical information. Your data is stored securely and is accessible only to authorized personnel."));

        faqList.add(new Faq("Can I schedule appointments for family members using the Swasthya Sangam application?", "Yes, you can schedule appointments for your family members using the Swasthya Sangam application. Simply log in to your account, select the 'Schedule Appointment' option, and provide the necessary details for your family member."));

        faqList.add(new Faq("How can I provide feedback or suggestions for improving the Swasthya Sangam application?", "We value your feedback and suggestions. You can submit your feedback through the 'Feedback' section in the application menu. Alternatively, you can email us at feedback@swasthyasangam.com. Your input helps us enhance the user experience and improve our services."));

        faqList.add(new Faq("What payment methods are accepted for booking lab tests and appointments?", "Swasthya Sangam accepts various payment methods, including credit/debit cards, net banking, and digital wallets. We also offer cash-on-delivery (COD) options for select services. Rest assured, all payment transactions are secure and encrypted."));

        faqList.add(new Faq("Is there a loyalty program or rewards system for regular users of Swasthya Sangam?", "Yes, Swasthya Sangam offers a loyalty program for regular users. You earn reward points for every transaction made through the application, which can be redeemed for discounts on future bookings and purchases. The more you use Swasthya Sangam, the more rewards you earn!"));

        faqList.add(new Faq("How can I update my profile information, such as contact details and address?", "You can easily update your profile information within the Swasthya Sangam application. Simply navigate to the 'Profile' section, select 'Edit Profile,' and make the necessary changes. Don't forget to save your updates before exiting."));

        faqList.add(new Faq("Is Swasthya Sangam available in multiple cities across India?", "Yes, Swasthya Sangam services are available in multiple cities across India, including Mumbai, Delhi, Bangalore, Chennai, Pune, and more. You can check the availability of services in your city within the application."));

        faqList.add(new Faq("Can I track the status of my lab test or medicine order through Swasthya Sangam?", "Yes, Swasthya Sangam provides real-time tracking for lab tests and medicine orders. You can track the status of your orders from the 'Order Details' section of the application. You'll receive notifications at every stage of the process."));

        faqList.add(new Faq("Are there any discounts or offers available for booking multiple services together?", "Yes, Swasthya Sangam frequently offers discounts and special offers for booking multiple services together. Keep an eye on the 'Offers' section of the application to avail of these exclusive deals. You can also subscribe to our newsletter for updates on upcoming promotions."));

        faqList.add(new Faq("What precautions are being taken during home blood collection services to ensure safety?", "Swasthya Sangam prioritizes the safety and well-being of its users. Our trained phlebotomists follow strict safety protocols during home blood collection, including wearing protective gear, sanitizing equipment, and maintaining social distancing norms. Rest assured, your safety is our top priority."));

        faqList.add(new Faq("Can I cancel or reschedule my doctor's appointment through Swasthya Sangam?", "Yes, you can cancel or reschedule your doctor's appointment through the Swasthya Sangam application. Simply navigate to the 'Appointments' section, select the appointment you wish to modify, and choose the appropriate option. Please note that cancellation and rescheduling policies may vary depending on the healthcare provider."));
    }

}