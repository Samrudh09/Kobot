from flask import Flask
from flask import send_file
from flask import send_from_directory
from flask import Flask, request , render_template
import os
from gensim.summarization import summarize

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello son'
def Listener():

    while True:
        # app.jinja_env.auto_reload = True
        app.run(host="0.0.0.0",threaded=True) # dont change this 

@app.route('/download/')
def file_downloads():
	# try:
	return send_from_directory("/Users/Samrudh/Desktop/deltahacks", "greetings.mp3",as_attachment=True)
	# except Exception as e:
	# 	return str(e)

@app.route('/audgen/', methods=['POST','GET'])
def result():

    arguments_1 = request.args.get("text").encode('utf-8') # will get all parameters after scan 
    print arguments_1


    return 'Received' , write1(str(arguments_1))

def write1(input_txt):
    with open('got_text.txt','w') as f:
        f.write(input_txt)
    os.system("say -v Daniel -o output.aiff -f got_text.txt")
    os.system("lame -m m output.aiff greetings.mp3")

# @app.route('/sumgen/', methods=['POST','GET'])
# def sum_write():
#     arguments_2 = request.args.get("ch",type=str)
#     arguments_2=(int(arguments_2))
 

#     return '''
#     <!doctype html>

# <html>
#     <head>

#     </head>
# <p>%s</p>

# </body>
# </html>

#     ''' %generator(arguments_2)

# def generator(p):
#     if p==1:
#         f = open('ch1.txt')
#         raw = f.read()
#         summary_boi=str(summarize(raw, ratio=0.2))
#         with open('summary.txt','w') as f:
#             f.write(summary_boi)

#     elif p ==2:
#         f = open('ch2.txt')
#         raw = f.read()
#         summary_boi=str(summarize(raw, ratio=0.2))
#         with open('summary.txt','w') as f:
#             f.write(summary_boi)
#     return raw





if __name__=='__main__':
    Listener()




